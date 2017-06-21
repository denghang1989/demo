package com.example.mqttretrofit.mqtt;

import com.example.mqttretrofit.utlis.MD5Utils;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.LinkedHashMap;
import java.util.Map;

public class ClientMqttClient {
    private final Map<String, Argument> mCallbackMap = new LinkedHashMap<>();
    private static ClientMqttClient instance;
    private MqttClient mqttClient;
    private MqttConnection mqttConnection;

    private ClientMqttClient(String mobile, String userId, String baseUrl) {
        try {
            mqttConnection = new MqttConnection(mobile, userId, baseUrl);
            mqttClient = new MqttClient(mqttConnection.baseUrl, mqttConnection.userId + "_2", new MemoryPersistence());
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

    public static ClientMqttClient getInstance(String mobile, String userId, String baseUrl) {
        if (instance == null) {
            instance = new ClientMqttClient(mobile, userId, baseUrl);
        }
        return instance;
    }

    public boolean isConnected() {
        return mqttClient != null && mqttClient.isConnected();
    }

    public void connect() {
        try {
            if (!isConnected()) {
                mqttClient.connect(mqttConnection.mqttConnectOptions);
                mqttClient.subscribe(mqttConnection.subscriptionTopic, mqttConnection.qos);
            }
        } catch (MqttException e) {
            e.printStackTrace();
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

    public void mqttClientReset() {
        disConnect();
        mqttClient = null;
        instance = null;
    }

    public static class MqttConnection {
        final String userId;
        final String baseUrl;
        final String mobile;
        final int qos = 1;
        final String subscriptionTopic;
        final MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();

        public MqttConnection(String mobile, String userId, String baseUrl) {
            this.mobile = mobile;
            this.userId = userId;
            this.baseUrl = baseUrl;
            this.mqttConnectOptions.setUserName(mobile);
            this.mqttConnectOptions.setPassword(MD5Utils.getMd5(mobile).toCharArray());
            this.mqttConnectOptions.setConnectionTimeout(0);
            this.mqttConnectOptions.setCleanSession(false);
            this.mqttConnectOptions.setAutomaticReconnect(true);
            this.subscriptionTopic = "cloudring/user/" + userId;
        }

        public String getUserId() {
            return userId;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public String getMobile() {
            return mobile;
        }
    }

}