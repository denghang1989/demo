package com.example.mqttretrofit.mqtt;

import android.support.annotation.Nullable;

import com.example.mqttretrofit.Callback;
import com.example.mqttretrofit.Platform;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import java.util.Map;

public class ClientCallback implements MqttCallback {
    private final Map<String, Callback<?>> mCallbackMap;
    private final Platform platform = Platform.get();

    public ClientCallback(Map<String, Callback<?>> callbackMap) {
        this.mCallbackMap = callbackMap;
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(@Nullable String topic, MqttMessage message) throws Exception {
        String content = new String(message.getPayload());
        JSONObject jsonObject = new JSONObject(content);
        String cmd = jsonObject.optString("cmd");
        Callback callback = mCallbackMap.get(cmd);

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
