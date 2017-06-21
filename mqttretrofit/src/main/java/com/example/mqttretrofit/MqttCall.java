package com.example.mqttretrofit;

import org.eclipse.paho.client.mqttv3.MqttClient;

/**
 * 2017/6/20 22
 */
public class MqttCall<T> implements Call<T> {
    private ServiceMethod mServiceMethod;
    private MqttClient    client;
    private Converter     converter;
    private Object[]      args;

    public MqttCall(MqttRetrofit mqttRetrofit, ServiceMethod serviceMethod, Converter converter, Object[] args) {
        this.mServiceMethod = serviceMethod;
        this.converter = converter;
        this.args = args;
        this.client = mqttRetrofit.getMqttClient();
    }

    @Override
    public void enqueue(Callback<T> callback) {

    }
}
