package com.example.mqttretrofit;

import android.util.Log;

import com.example.mqttretrofit.adpter.CallAdapter;
import com.example.mqttretrofit.annotation.Body;
import com.example.mqttretrofit.annotation.Cmd;
import com.example.mqttretrofit.annotation.CommandName;
import com.example.mqttretrofit.annotation.Query;
import com.example.mqttretrofit.annotation.QueryMap;
import com.example.mqttretrofit.annotation.Topic;
import com.example.mqttretrofit.converter.Converter;
import com.example.mqttretrofit.utlis.Check;
import com.example.mqttretrofit.utlis.Utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

/**
 * @date 2017/6/20 11
 */
public class ServiceMethod<R, T> {
    private static final String TAG = "ServiceMethod";
    private String cmd;
    private String cmdName;
    private String topic;
    final CallAdapter<R, T> mCallAdapter;
    Converter<String, R> responseConverter;
    final ParameterHandler<?>[] parameterHandlers;
    private MqttRetrofit mMqttRetrofit;

    public ServiceMethod(Builder<R, T> builder) {
        this.cmd = builder.cmdValue;
        this.topic = builder.topic;
        this.mCallAdapter = builder.mCallAdapter;
        this.responseConverter = builder.responseConverter;
        this.parameterHandlers = builder.parameterHandlers;
        this.mMqttRetrofit = builder.mMqttRetrofit;
        this.cmdName = builder.cmdName;
    }

    @SuppressWarnings("unchecked")
    public String toRequest(Object[] args) {
        Map<String, String> requestMap = new HashMap<>();
        int argumentCount = args != null ? args.length : 0;
        ParameterHandler<Object>[] handlers = (ParameterHandler<Object>[]) parameterHandlers;
        if (argumentCount != handlers.length) {
            throw new IllegalArgumentException("Argument count (" + argumentCount
                    + ") doesn't match expected count (" + handlers.length + ")");
        }
        for (int p = 0; p < argumentCount; p++) {
            handlers[p].apply(requestMap, args[p]);
        }
        requestMap.put(cmdName, cmd);
        String requestString = mMqttRetrofit.mapToString(requestMap);
        Log.d(TAG, "toRequest: " + requestString);
        return requestString;
    }

    public static final class Builder<R, T> {
        private String cmdValue;
        private String cmdName;
        private String topic;
        final Method method;
        final Annotation[] methodAnnotations; // 方法注解 必须制定（cmdValue，topic）
        final Annotation[][] parameterAnnotationsArray; //parameter注解 返回2位数组，可能有多个注解
        final Type[] parameterTypes; //parameter 数据类型 （java8 可以使用Parameter.java 一个类全部搞定）

        CallAdapter<R, T> mCallAdapter; //返回数据类型的适配器
        Converter<String, R> responseConverter; //数据解析工厂
        ParameterHandler<?>[] parameterHandlers; //parameter 注解处理包装类
        private MqttRetrofit mMqttRetrofit;
        Type responseType; // 返回值数据类型 （默认 Call<?>）

        public Builder(MqttRetrofit mqttRetrofit, Method method) {
            this.mMqttRetrofit = mqttRetrofit; //retrofit
            this.method = method; //方法
            this.methodAnnotations = method.getAnnotations(); // 方法注解
            this.parameterAnnotationsArray = method.getParameterAnnotations(); //parameter注解
            this.parameterTypes = method.getGenericParameterTypes(); //parameter 数据类型
        }

        public ServiceMethod build() {
            for (Annotation annotation : methodAnnotations) {
                parseMethodAnnotation(annotation);
            }
            Check.checkNotNull(cmdValue, method.getName() + "方法上面缺少@cmd注解");
            Check.checkNotNull(topic, method.getName() + "方法上面缺少@Topic注解");

            mCallAdapter = createCallAdapter();
            responseType = createCallAdapter().responseType();
            responseConverter = createResponseConverter();

            int parameterCount = parameterAnnotationsArray.length;
            parameterHandlers = new ParameterHandler<?>[parameterCount];
            for (int i = 0; i < parameterCount; i++) {
                Type parameterType = parameterTypes[i];
                if (Utils.hasUnresolvableType(parameterType)) {
                    throw parameterError(i, "Parameter type must not include a type variable or wildcard: %s",
                            parameterType);
                }
                Annotation[] parameterAnnotations = parameterAnnotationsArray[i];
                if (parameterAnnotations == null) {
                    throw parameterError(i, "No Retrofit annotation found.");
                }
                parameterHandlers[i] = parameter(i, parameterType, parameterAnnotations);
            }
            return new ServiceMethod(this);
        }

        private ParameterHandler<?> parameter(int i, Type parameterType, Annotation[] annotations) {
            ParameterHandler<?> result = null;
            for (Annotation annotation : annotations) {
                ParameterHandler<?> annotationAction = parseParameterAnnotation(i, parameterType, annotations, annotation);
                if (annotationAction == null) {
                    continue;
                }
                if (result != null) {
                    throw parameterError(i, "Multiple Retrofit annotations found, only one allowed.");
                }
                result = annotationAction;
            }

            if (result == null) {
                throw parameterError(i, "No Retrofit annotation found.");
            }

            return result;
        }

        private ParameterHandler<?> parseParameterAnnotation(int i, Type parameterType, Annotation[] annotations, Annotation annotation) {
            if (annotation instanceof Query) {
                Query query = (Query) annotation;
                String name = query.value();
                Class<?> rawParameterType = Utils.getRawType(parameterType);
                if (Iterable.class.isAssignableFrom(rawParameterType)) {
                    throw parameterError(i, "not only support Iterable,只是支持基础类型，英文我不会啊");
                } else if (rawParameterType.isArray()) {
                    throw parameterError(i, "not only support Array,只是支持基础类型，英文我不会啊 ");
                } else {
                    Converter<?, String> converter = mMqttRetrofit.stringConverter(parameterType, annotations);
                    return new ParameterHandler.Query<>(name, converter);
                }

            } else if (annotation instanceof QueryMap) {
                Class<?> rawParameterType = Utils.getRawType(parameterType);
                if (!Map.class.isAssignableFrom(rawParameterType)) {
                    throw parameterError(i, "@QueryMap parameter parameterType must be Map.");
                }
                Type mapType = Utils.getSupertype(parameterType, rawParameterType, Map.class);
                if (!(mapType instanceof ParameterizedType)) {
                    throw parameterError(i, "Map must include generic types (e.g., Map<String, String>)");
                }
                Type[] actualTypeArguments = ((ParameterizedType) mapType).getActualTypeArguments();
                if (!actualTypeArguments[0].equals(String.class) && !actualTypeArguments[1].equals(String.class)) {
                    throw parameterError(i, "only support Map<String, String>");
                }
                return new ParameterHandler.QueryMap<>();
            } else if (annotation instanceof Body) {
                Converter<?, String> converter;
                try {
                    converter = mMqttRetrofit.requestBodyConverter(parameterType, annotations, methodAnnotations);
                } catch (RuntimeException e) {
                    throw parameterError(i, "Unable to create @Body converter for %s", parameterType);
                }
                return new ParameterHandler.Body<>(converter);
            }

            return null;
        }

        private Converter<String, R> createResponseConverter() {
            Annotation[] annotations = method.getAnnotations();
            try {
                return mMqttRetrofit.responseBodyConverter(responseType, annotations);
            } catch (RuntimeException e) {
                throw methodError(e, "Unable to create converter for %s", responseType);
            }
        }

        private RuntimeException parameterError(int p, String message, Object... args) {
            return methodError(message + " (parameter #" + (p + 1) + ")", args);
        }

        /**
         * @return 创建返回值的适配器 （默认返回 call<T> :  如果处理：call<T>--->Observable<T>）
         */
        @SuppressWarnings("unchecked")
        private CallAdapter<R, T> createCallAdapter() {
            Type returnType = method.getGenericReturnType();
            if (Utils.hasUnresolvableType(returnType)) {
                throw methodError(
                        "Method return type must not include a type variable or wildcard: %s", returnType);
            }
            if (returnType == Void.class) {
                throw methodError("Service methods cannot return void.");
            }
            Annotation[] annotations = method.getAnnotations();
            try {
                CallAdapter<R, T> callAdapter = (CallAdapter<R, T>) mMqttRetrofit.callAdapter(returnType, annotations);
                return callAdapter;
            } catch (Exception e) {
                throw methodError(e, "Unable to create call adapter for %s", returnType);
            }
        }

        private RuntimeException methodError(String message, Object... args) {
            return methodError(null, message, args);
        }

        private RuntimeException methodError(Throwable cause, String message, Object... args) {
            message = String.format(message, args);
            return new IllegalArgumentException(message
                    + "\n    for method "
                    + method.getDeclaringClass().getSimpleName()
                    + "."
                    + method.getName(), cause);
        }

        private void parseMethodAnnotation(Annotation methodAnnotation) {
            if (methodAnnotation instanceof Cmd) {
                this.cmdValue = ((Cmd) methodAnnotation).value();
                this.cmdName = Cmd.class.getAnnotation(CommandName.class).value();
            } else if (methodAnnotation instanceof Topic) {
                this.topic = ((Topic) methodAnnotation).value();
            }
        }
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    static Class<?> boxIfPrimitive(Class<?> type) {
        if (boolean.class == type)
            return Boolean.class;
        if (byte.class == type)
            return Byte.class;
        if (char.class == type)
            return Character.class;
        if (double.class == type)
            return Double.class;
        if (float.class == type)
            return Float.class;
        if (int.class == type)
            return Integer.class;
        if (long.class == type)
            return Long.class;
        if (short.class == type)
            return Short.class;
        return type;
    }
}
