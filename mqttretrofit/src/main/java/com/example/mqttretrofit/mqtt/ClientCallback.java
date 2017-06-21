package com.example.mqttretrofit.mqtt;

import android.support.annotation.Nullable;

import com.example.mqttretrofit.Callback;
import com.example.mqttretrofit.Converter;
import com.example.mqttretrofit.Platform;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Map;

public class ClientCallback implements MqttCallback {
    private final Map<String, Argument> mCallbackMap;
    private final Platform platform = Platform.get();

    public ClientCallback(Map<String, Argument> callbackMap) {
        this.mCallbackMap = callbackMap;
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(@Nullable String topic, MqttMessage message) throws Exception {
        final String content = new String(message.getPayload());
        JSONObject jsonObject = new JSONObject(content);
        String cmd = jsonObject.optString("cmd");
        Argument argument = mCallbackMap.get(cmd);
        if (argument != null) {
            final Callback callback = argument.getCallback();
            Converter converter = argument.getConverter();
            try {
                final Object convert = converter.convert(content);
                platform.execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onSuccess(convert);
                    }
                });
            } catch (final IOException e) {
                e.printStackTrace();
                platform.execute(new Runnable() {
                    @Override
                    public void run() {
                        callback.onError(e);
                    }
                });
            }
        }

    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
