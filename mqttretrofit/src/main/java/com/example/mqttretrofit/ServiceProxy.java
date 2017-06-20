package com.example.mqttretrofit;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @date 2017/6/20 10
 */
class ServiceProxy implements InvocationHandler {
    private MqttRetrofit mMqttRetrofit;

    public ServiceProxy(MqttRetrofit mqttRetrofit) {
        mMqttRetrofit = mqttRetrofit;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }
        ServiceMethod serviceMethod = loadServiceMethod(method);
        String topic = serviceMethod.getTopic();
        if (topic == null) {
            throw new IllegalArgumentException(method.getName() + "方法上面缺少@Topic注解");
        }
        MqttMessage message = serviceMethod.getMessage(args);
        mMqttRetrofit.getClientMqttClient().publish(topic, message);
        return null;
    }

    private ServiceMethod loadServiceMethod(Method method) {
        ServiceMethod result;
        synchronized (mMqttRetrofit.getServiceMethodCache()) {
            result = mMqttRetrofit.getServiceMethodCache().get(method);
            if (result == null) {
                result = new ServiceMethod.Builder(mMqttRetrofit, method).build();
                mMqttRetrofit.getServiceMethodCache().put(method, result);
            }
        }
        return result;
    }

}
