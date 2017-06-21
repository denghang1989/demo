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
        ServiceMethod serviceMethod = loadServiceMethod(method,args);
        Converter converter = mMqttRetrofit.converter(serviceMethod.actualType);
        MqttCall mqttCall = new MqttCall(mMqttRetrofit, serviceMethod,converter);
        return mMqttRetrofit.getCallAdapter().adapt(mqttCall);
    }

    private ServiceMethod loadServiceMethod(Method method, Object[] args) {
        ServiceMethod result;
        synchronized (mMqttRetrofit.getServiceMethodCache()) {
            result = mMqttRetrofit.getServiceMethodCache().get(method);
            if (result == null) {
                result = new ServiceMethod.Builder(method,args).build();
                mMqttRetrofit.getServiceMethodCache().put(method, result);
            }
        }
        return result;
    }

}
