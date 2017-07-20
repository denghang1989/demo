package com.example.mqttretrofit;

import com.example.mqttretrofit.adpter.CallAdapter;
import com.example.mqttretrofit.annotation.Body;
import com.example.mqttretrofit.annotation.Cmd;
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
    private HashMap<String, String> mParameterMap = new HashMap<>();
    private String cmd;
    private String topic;
    protected Type actualType; //MqttCall<?> 里面的type
    final CallAdapter<R, T> mCallAdapter;

    public ServiceMethod(Builder builder) {
        this.cmd = builder.cmd;
        this.topic = builder.topic;
        this.actualType = builder.actualType;
        this.mCallAdapter = builder.mCallAdapter;
    }

    public static final class Builder<T, R> {
        final Method method;
        final Annotation[] methodAnnotations;
        final Annotation[][] parameterAnnotationsArray;
        final Type[] parameterTypes; //参数类型 （java8 可以使用Parameter.java 一个类全部搞定）

        CallAdapter<T, R> mCallAdapter;
        Converter<String, T> responseConverter;
        ParameterHandler<?>[] parameterHandlers;
        protected Type actualType;
        private String cmd;
        private String topic;
        private MqttRetrofit mMqttRetrofit;
        Type responseType;

        public Builder(MqttRetrofit mqttRetrofit, Method method) {
            this.mMqttRetrofit = mqttRetrofit;
            this.method = method;
            this.methodAnnotations = method.getAnnotations();
            this.parameterAnnotationsArray = method.getParameterAnnotations();
            this.parameterTypes = method.getGenericParameterTypes();
        }

        public ServiceMethod build() {
            Check.checkNotNull(cmd, method.getName() + "方法上面缺少@cmd注解");
            Check.checkNotNull(topic, method.getName() + "方法上面缺少@Topic注解");

            mCallAdapter = createCallAdapter();
            responseType = createCallAdapter().responseType();
            responseConverter = createResponseConverter();
            for (Annotation annotation : methodAnnotations) {
                parseMethodAnnotation(annotation);
            }

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

        private ParameterHandler<?> parseParameterAnnotation(int i, Type type, Annotation[] annotations, Annotation annotation) {
            if (annotation instanceof Query) {
                Query query = (Query) annotation;
                String name = query.value();
                Class<?> rawParameterType = Utils.getRawType(type);
                if (Iterable.class.isAssignableFrom(rawParameterType)) {
                    if (!(type instanceof ParameterizedType)) {
                        throw parameterError(i, rawParameterType.getSimpleName()
                                + " must include generic type (e.g., "
                                + rawParameterType.getSimpleName()
                                + "<String>)");
                    }
                    ParameterizedType parameterizedType = (ParameterizedType) type;
                    Type iterableType = Utils.getParameterUpperBound(0, parameterizedType);
                    Converter<?, String> converter = mMqttRetrofit.stringConverter(iterableType, annotations);
                    return new ParameterHandler.Query<>(name, converter).iterable();
                } else if (rawParameterType.isArray()) {
                    Class<?> arrayComponentType = boxIfPrimitive(rawParameterType.getComponentType());
                    Converter<?, String> converter = mMqttRetrofit.stringConverter(arrayComponentType, annotations);
                    return new ParameterHandler.Query<>(name, converter).array();
                } else {
                    Converter<?, String> converter = mMqttRetrofit.stringConverter(type, annotations);
                    return new ParameterHandler.Query<>(name, converter);
                }

            } else if (annotation instanceof QueryMap) {
                Class<?> rawParameterType = Utils.getRawType(type);
                if (!Map.class.isAssignableFrom(rawParameterType)) {
                    throw parameterError(i, "@QueryMap parameter type must be Map.");
                }
                Type mapType = Utils.getSupertype(type, rawParameterType, Map.class);
                if (!(mapType instanceof ParameterizedType)) {
                    throw parameterError(i, "Map must include generic types (e.g., Map<String, String>)");
                }
                return new ParameterHandler.QueryMap<>();
            } else if (annotation instanceof Body) {
                Converter<?, Map<String,String>> converter;
                try {
                    converter = mMqttRetrofit.requestBodyConverter(type, annotations, methodAnnotations);
                } catch (RuntimeException e) {
                    // Wide exception range because factories are user code.
                    throw parameterError(i, "Unable to create @Body converter for %s", type);
                }
                return new ParameterHandler.Body<>(converter);
            }

            return null;
        }

        private Converter<String, T> createResponseConverter() {
            Annotation[] annotations = method.getAnnotations();
            try {
                return mMqttRetrofit.responseBodyConverter(responseType, annotations);
            } catch (RuntimeException e) { // Wide exception range because factories are user code.
                throw methodError(e, "Unable to create converter for %s", responseType);
            }
        }

        private RuntimeException parameterError(int p, String message, Object... args) {
            return methodError(message + " (parameter #" + (p + 1) + ")", args);
        }

        private CallAdapter<T, R> createCallAdapter() {
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
                CallAdapter<T, R> callAdapter = (CallAdapter<T, R>) mMqttRetrofit.callAdapter(returnType, annotations);
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


        private void parseMethodAnnotations() {
            for (int i = 0; i < methodAnnotations.length; i++) {
                Annotation methodAnnotation = methodAnnotations[i];
                parseMethodAnnotation(methodAnnotation);
            }
        }

        private void parseMethodAnnotation(Annotation methodAnnotation) {
            if (methodAnnotation instanceof Cmd) {
                this.cmd = ((Cmd) methodAnnotation).value();
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
