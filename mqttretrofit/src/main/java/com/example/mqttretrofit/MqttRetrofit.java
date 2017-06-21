package com.example.mqttretrofit;

import com.example.mqttretrofit.mqtt.ClientMqttClient;

import org.eclipse.paho.client.mqttv3.MqttClient;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.example.mqttretrofit.utlis.Utils.checkNotNull;

/**
 * @date 2017/6/20 10
 */
public class MqttRetrofit {

    private final Map<Method, ServiceMethod> serviceMethodCache = new LinkedHashMap<>();
    private final Converter.Factory mConverter;
    private final CallAdapter       mCallAdapter;
    private final ClientMqttClient  mClientMqttClient;

    private MqttRetrofit(Builder builder) {
        mConverter = builder.mConverter;
        mCallAdapter = builder.mCallAdapter;
        mClientMqttClient = builder.mClientMqttClient;
    }

    public <T> T create(Class<T> clazz) {
        if (!clazz.isInterface()) {
            throw new IllegalArgumentException();
        }
        T t = (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new ServiceProxy(this));
        return t;
    }

    public MqttClient getMqttClient() {
        return mClientMqttClient.getMqttClient();
    }

    public Map<Method, ServiceMethod> getServiceMethodCache() {
        return serviceMethodCache;
    }

    public CallAdapter getCallAdapter() {
        return mCallAdapter;
    }

    public Converter converter(Type actualType) {
        return mConverter.getConverter(actualType);
    }

    public static final class Builder {
        private Converter.Factory   mConverter;
        private CallAdapter mCallAdapter;
        private ClientMqttClient    mClientMqttClient;

        public Builder setMqttClinet(ClientMqttClient mqttClinet) {
            checkNotNull(mqttClinet, "mqttClient = null");
            this.mClientMqttClient = mqttClinet;
            return this;
        }

        public Builder setConverterFactory(Converter.Factory factory) {
            this.mConverter = factory;
            return this;
        }

        public Builder setCallAdapterFactory(CallAdapter factory) {
            this.mCallAdapter = factory;
            return this;
        }

        public MqttRetrofit build() {
            return new MqttRetrofit(this);
        }
    }


}
