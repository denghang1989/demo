package com.example.mqttretrofit;

import com.example.mqttretrofit.adpter.CallAdapter;
import com.example.mqttretrofit.adpter.DefaultCallAdapterFactory;
import com.example.mqttretrofit.converter.Converter;
import com.example.mqttretrofit.converter.GsonConverterFactory;
import com.example.mqttretrofit.mqtt.Argument;
import com.example.mqttretrofit.mqtt.ClientMqttClient;

import org.eclipse.paho.client.mqttv3.MqttClient;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.mqttretrofit.utlis.Check.checkNotNull;

/**
 * @date 2017/6/20 10
 */
public class MqttRetrofit {

    private final Map<Method, ServiceMethod<?, ?>> serviceMethodCache = new ConcurrentHashMap<>();
    private Converter.Factory mConverterFactory;
    private CallAdapter.Factory mCallAdapterFactory;
    private ClientMqttClient mClientMqttClient;

    private MqttRetrofit(Builder builder) {
        mConverterFactory = builder.mConverterFactory;
        mCallAdapterFactory = builder.mCallAdapterFactory;
        mClientMqttClient = builder.mClientMqttClient;
    }

    public <T> T create(final Class<T> clazz) {
        if (!clazz.isInterface()) {
            throw new IllegalArgumentException();
        }

        T t = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new ServiceProxy(this));
        return t;
    }

    public MqttClient getMqttClient() {
        return mClientMqttClient.getMqttClient();
    }

    public Map<Method, ServiceMethod<?, ?>> getServiceMethodCache() {
        return serviceMethodCache;
    }

    public Map<String, Argument> getCallbackMap() {
        return mClientMqttClient.getCallbackMap();
    }

    public <T> Converter<String, T> responseBodyConverter(Type responseType, Annotation[] annotations) {
        return nextResponseBodyConverter(responseType, annotations);
    }

    private <T> Converter<String, T> nextResponseBodyConverter(Type type, Annotation[] annotations) {
        checkNotNull(type, "type == null");
        checkNotNull(annotations, "annotations == null");
        Converter<String, ?> converter = mConverterFactory.responseBodyConverter(type, annotations);
        if (converter != null) {
            //noinspection unchecked
            return (Converter<String, T>) converter;
        }
        return null;
    }

    public Converter<?, String> stringConverter(Type type, Annotation[] annotations) {
        return mConverterFactory.stringConverter(type, annotations);
    }

    public Converter<?, String> requestBodyConverter(Type type, Annotation[] annotations, Annotation[] methodAnnotations) {
        return mConverterFactory.requestBodyConverter(type, annotations, methodAnnotations);
    }

    public String mapToString(Map<String, String> requestMap) {
        return mConverterFactory.mapToString(requestMap);
    }

    public static final class Builder {
        private Converter.Factory mConverterFactory;
        private CallAdapter.Factory mCallAdapterFactory;
        private ClientMqttClient mClientMqttClient;

        public Builder setMqttClient(ClientMqttClient mqttClientt) {
            checkNotNull(mqttClientt, "mqttClient = null");
            this.mClientMqttClient = mqttClientt;
            if (mCallAdapterFactory == null) {
                mCallAdapterFactory = DefaultCallAdapterFactory.INSTANCE;
            }
            if (mConverterFactory == null) {
                mConverterFactory = new GsonConverterFactory();
            }
            return this;
        }

        public Builder setConverterFactory(Converter.Factory factory) {
            this.mConverterFactory = factory;
            return this;
        }

        public Builder setCallAdapterFactory(CallAdapter.Factory factory) {
            this.mCallAdapterFactory = factory;
            return this;
        }

        public MqttRetrofit build() {
            return new MqttRetrofit(this);
        }
    }

    public CallAdapter<?, ?> callAdapter(Type returnType, Annotation[] annotations) {
        return nextCallAdapter(returnType, annotations);
    }

    public CallAdapter<?, ?> nextCallAdapter(Type returnType, Annotation[] annotations) {
        checkNotNull(returnType, "returnType == null");
        checkNotNull(annotations, "annotations == null");
        CallAdapter<?, ?> adapter = mCallAdapterFactory.get(returnType, annotations);
        if (adapter != null) {
            return adapter;
        } else {
            throw new IllegalArgumentException("参数错误");
        }
    }
}
