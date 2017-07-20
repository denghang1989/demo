package com.example.mqttretrofit;

import android.support.annotation.Nullable;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

/**
 * 2017/6/20 22
 */
public class MqttCall<T> implements Call<T> {
    private final ServiceMethod<T, ?> serviceMethod;
    private final Object[] args;
    private final MqttRetrofit mMqttRetrofit;

    public MqttCall(ServiceMethod<T, ?> serviceMethod, @Nullable Object[] args, MqttRetrofit mqttClient) {
        this.serviceMethod = serviceMethod;
        this.mMqttRetrofit = mqttClient;
        this.args = args;
    }

    @Override
    public void enqueue(Callback<T> callback) {
        String cmd = serviceMethod.getCmd();
        String topic = serviceMethod.getTopic();
        MqttMessage mqttMessage = new MqttMessage();
        try {
            mMqttRetrofit.getMqttClient().publish(topic, mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
        }

    }
}
