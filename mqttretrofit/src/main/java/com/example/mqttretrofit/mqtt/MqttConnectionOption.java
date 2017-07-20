package com.example.mqttretrofit.mqtt;

import com.example.mqttretrofit.utlis.MD5Utils;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;

public class MqttConnectionOption {
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
        mqttConnectOptions.setUserName(mobile);
        mqttConnectOptions.setPassword(MD5Utils.getMd5(mobile).toCharArray());
        mqttConnectOptions.setConnectionTimeout(1000);
        mqttConnectOptions.setCleanSession(false);
        mqttConnectOptions.setKeepAliveInterval(60);
        mqttConnectOptions.setAutomaticReconnect(true);
        subscriptionTopic = "cloudring/user/" + userId;
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