package com.example.mqttretrofit.mqtt;

import com.example.mqttretrofit.Callback;
import com.example.mqttretrofit.utlis.MD5Utils;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.LinkedHashMap;
import java.util.Map;

public class ClientMqttClient {
    private final Map<String, Callback<?>> mCallbackMap = new LinkedHashMap<>();
    private static ClientMqttClient instance;
    private MqttClient mqttClient;
    private MqttConnection mqttConnection;
    private ClientCallback mClientCallback;

    private ClientMqttClient(MqttConnection connection) {
        this.mqttConnection = connection;
        try {
            mqttClient = new MqttClient(mqttConnection.baseUrl, mqttConnection.userId + "_2", new MemoryPersistence());
            mqttClient.setCallback(mClientCallback = new ClientCallback(mCallbackMap));
            connect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Callback<?>> getCallbackMap() {
        return mCallbackMap;
    }

    public MqttClient getMqttClient() {
        return mqttClient;
    }

    /**
     * @param mobile
     * @param useId
     * @param baseUrl
     * @return
     */
    public static ClientMqttClient getInstance(String mobile, String useId, String baseUrl) {
        if (instance == null)
            instance = new ClientMqttClient(new MqttConnection(mobile, useId, baseUrl));
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

    private static class MqttConnection {
        final String userId;
        final String baseUrl;
        final char[] password;
        final int qos = 1;
        final String subscriptionTopic;
        final MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();

        public MqttConnection(String mobile, String userId, String baseUrl) {
            this.userId = userId;
            this.baseUrl = baseUrl;
            this.password = MD5Utils.getMd5(mobile).toCharArray();
            this.mqttConnectOptions.setUserName(mobile);
            this.mqttConnectOptions.setPassword(password);
            this.mqttConnectOptions.setConnectionTimeout(0);
            this.mqttConnectOptions.setCleanSession(false);
            this.mqttConnectOptions.setAutomaticReconnect(true);
            this.subscriptionTopic = "cloudring/user/" + userId;
        }
    }

}