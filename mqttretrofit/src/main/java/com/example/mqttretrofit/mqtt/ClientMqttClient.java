package com.example.mqttretrofit.mqtt;

import android.util.Log;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.LinkedHashMap;
import java.util.Map;

public class ClientMqttClient {
    private static final String TAG = "ClientMqttClient";
    private final Map<String, Argument> mCallbackMap = new LinkedHashMap<>();
    private MqttClient mqttClient;
    private MqttConnectionOption mMqttConnectionOption;

    public ClientMqttClient(MqttConnectionOption mqttConnection) {
        try {
            mMqttConnectionOption = mqttConnection;
            mqttClient = new MqttClient(mMqttConnectionOption.baseUrl, mMqttConnectionOption.userId, new MemoryPersistence());
            mqttClient.setCallback(new ClientCallback(mCallbackMap));
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Argument> getCallbackMap() {
        return mCallbackMap;
    }

    public MqttClient getMqttClient() {
        return mqttClient;
    }

    public boolean isConnected() {
        return mqttClient != null && mqttClient.isConnected();
    }

    public void connect() {
        try {
            if (!isConnected()) {
                mqttClient.connect(mMqttConnectionOption.mqttConnectOptions);
                mqttClient.subscribe(mMqttConnectionOption.subscriptionTopic, mMqttConnectionOption.qos);
            }
        } catch (MqttException e) {
            e.printStackTrace();
            Log.d(TAG, "connect: " + e.getMessage());
        }
    }

    public void disConnect() {
        if (mqttClient != null && mqttClient.isConnected()) {
            try {
                mqttClient.disconnect();
            } catch (MqttException e) {
                e.printStackTrace();
            }
        }
    }

    public void mqttClientRelease() {
        disConnect();
        mqttClient = null;
    }
}