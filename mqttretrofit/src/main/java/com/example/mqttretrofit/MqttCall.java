package com.example.mqttretrofit;

import com.example.mqttretrofit.converter.Converter;
import com.example.mqttretrofit.mqtt.Argument;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;

import java.util.Map;

/**
 * 2017/6/20 22
 */
public class MqttCall<T> implements Call<T> {
    private final Converter converter;
    private final ServiceMethod mServiceMethod;
    private final MqttClient client;
    private final Map<String, Argument> mCallbackMap;

    public MqttCall(MqttRetrofit mqttRetrofit, ServiceMethod serviceMethod, Converter converter) {
        this.mServiceMethod = serviceMethod;
        this.client = mqttRetrofit.getMqttClient();
        this.mCallbackMap = mqttRetrofit.getCallbackMap();
        this.converter = converter;
    }

    @Override
    public void enqueue(Callback<T> callback) {
        try {
            Argument<T> argument = new Argument(converter, callback);
            mCallbackMap.put(mServiceMethod.getCmd() + "_resp", argument);
            client.publish(mServiceMethod.getTopic(), mServiceMethod.getMessage());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }
}
