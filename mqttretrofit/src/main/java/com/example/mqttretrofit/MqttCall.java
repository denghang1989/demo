package com.example.mqttretrofit;

import android.support.annotation.Nullable;

import com.example.mqttretrofit.mqtt.Argument;

import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.util.Map;

/**
 * 2017/6/20 22
 */
public class MqttCall<T> implements Call<T> {
    private final ServiceMethod<T, ?> serviceMethod;
    private final Object[] args;
    private final MqttRetrofit mMqttRetrofit;
    private final Map<String, Argument> mCallbackMap;

    public MqttCall(ServiceMethod<T, ?> serviceMethod, @Nullable Object[] args, MqttRetrofit mqttClient) {
        this.serviceMethod = serviceMethod;
        this.mMqttRetrofit = mqttClient;
        this.args = args;
        this.mCallbackMap = mMqttRetrofit.getCallbackMap();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void enqueue(Callback<T> callback) {
        String cmd_resp = serviceMethod.getCmd()+"_resp";
        String topic = serviceMethod.getTopic();
        try {
            String request = createRawRequest();
            mCallbackMap.put(cmd_resp,new Argument(serviceMethod.responseConverter,callback));
            MqttMessage mqttMessage = new MqttMessage(request.getBytes());
            mMqttRetrofit.getMqttClient().publish(topic, mqttMessage);
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    private String createRawRequest() {
        return serviceMethod.toRequest(args);
    }
}
