package com.example.mqttretrofit.devicebusiness.devicemqtt;

import com.example.mqttretrofit.Callback;
import com.example.mqttretrofit.ServiceMethod;
import com.example.mqttretrofit.utlis.MD5Utils;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.Map;

public class ClientMqttClient {

    private MqttClient mqttClient;
    private MqttConnection mqttConnection;
    private static ClientMqttClient instance;
    private static ClientCallback handler;

    private ClientMqttClient(MqttConnection connection) {
        this.mqttConnection = connection;
        try {
            mqttClient = new MqttClient(mqttConnection.baseUrl, mqttConnection.userId + "_2", new MemoryPersistence());
            mqttClient.setCallback(handler = new ClientCallback());
            connect();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    public Map<String, Callback> getMapCallback() {
        return handler.getCallbackMap();
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

    public void publish(String topicName, String msg) {
        try {
            MqttMessage message = new MqttMessage(msg.getBytes());
            mqttClient.getTopic(topicName).publish(message);
        } catch (Exception ex) {
            ex.printStackTrace();
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

    public void publish(ServiceMethod serviceMethod, Object[] args) {

    }

    static class MqttConnection {
        final String userId;
        final String baseUrl;
        final char[] password;
        final int qos = 1;
        final String subscriptionTopic;
        final MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();

        public MqttConnection(String mobile, String userId, String baseUrl) {
            this.userId = userId;
            this.baseUrl = baseUrl;
            password = MD5Utils.getMd5(mobile).toCharArray();
            this.mqttConnectOptions.setUserName(mobile);
            this.mqttConnectOptions.setPassword(password);
            this.mqttConnectOptions.setConnectionTimeout(0);
            this.mqttConnectOptions.setCleanSession(false);
            this.mqttConnectOptions.setAutomaticReconnect(true);
            this.subscriptionTopic = "cloudring/user/" + userId;
        }
    }

}