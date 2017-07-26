package com.example.mqttretrofit.mqtt;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

public class MqttConnectionOption {

    private static final String KEY_NAME = "01a4ec05b64d4511ac2500ab0c0654f7";
    private static final String KEY_PASSWORD = "6e2cfe4abce5428fa2d27fb44c444289";

    public String userId;
    public String baseUrl;
    public String mobile;
    public int qos = 1;
    public String subscriptionTopic;
    public MqttConnectOptions mqttConnectOptions = new MqttConnectOptions();

    private MqttConnectionOption(Builder builder) {
        userId = builder.userId;
        baseUrl = builder.baseUrl;
        mobile = builder.mobile;
        mqttConnectOptions.setUserName(KEY_NAME);
        mqttConnectOptions.setPassword(KEY_PASSWORD.toCharArray());
        mqttConnectOptions.setConnectionTimeout(1000);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setKeepAliveInterval(60);
        mqttConnectOptions.setAutomaticReconnect(true);
        subscriptionTopic = "cloudring/device/" + userId;
    }

    public static final class Builder {
        private String userId;
        private String baseUrl;
        private String mobile;

        public Builder() {
        }

        public Builder userId(String val) {
            userId = val;
            return this;
        }

        public Builder baseUrl(String val) {
            baseUrl = val;
            return this;
        }

        public Builder mobile(String val) {
            mobile = val;
            return this;
        }

        public MqttConnectionOption build() {
            return new MqttConnectionOption(this);
        }
    }
}