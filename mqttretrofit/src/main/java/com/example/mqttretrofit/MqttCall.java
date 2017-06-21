package com.example.mqttretrofit;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Map;

/**
 * 2017/6/20 22
 */
public class MqttCall<T> implements Call<T> {
    private ServiceMethod mServiceMethod;
    private MqttClient client;
    private Map<String, Callback<?>> mCallbackMap;

    public MqttCall(MqttRetrofit mqttRetrofit, ServiceMethod serviceMethod) {
        this.mServiceMethod = serviceMethod;
        this.client = mqttRetrofit.getMqttClient();
        this.mCallbackMap = mqttRetrofit.getCallbackMap();
    }

    @Override
    public void enqueue(Callback<T> callback) {
        try {
            mCallbackMap.put(mServiceMethod.getCmd() + "_resp", callback);
            client.publish(mServiceMethod.getTopic(), mServiceMethod.getMessage());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
