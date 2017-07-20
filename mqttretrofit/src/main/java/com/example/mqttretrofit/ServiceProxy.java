package com.example.mqttretrofit;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @date 2017/6/20 10
 */
public class ServiceProxy implements InvocationHandler {
    private MqttRetrofit mMqttRetrofit;

    public ServiceProxy(MqttRetrofit mqttRetrofit) {
        mMqttRetrofit = mqttRetrofit;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getDeclaringClass() == Object.class) {
            return method.invoke(this, args);
        }
        ServiceMethod<Object, Object> serviceMethod = (ServiceMethod<Object, Object>) loadServiceMethod(method);
        MqttCall<Object> mqttCall = new MqttCall<>(serviceMethod, args,mMqttRetrofit);
        return serviceMethod.mCallAdapter.adapt(mqttCall);
    }

    private ServiceMethod<?, ?> loadServiceMethod(Method method) {
        ServiceMethod<?, ?> result = mMqttRetrofit.getServiceMethodCache().get(method);
        if (result != null) {
            return result;
        }
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
