package com.example.mqttretrofit.mqtt;

import com.example.mqttretrofit.Callback;
import com.example.mqttretrofit.converter.Converter;

/**
 * @date 2017/6/21 10
 */
public class Argument<T> {
    private Converter mConverter;
    private Callback<T> mCallback;

    public Argument(Converter converter, Callback<T> callback) {
        mConverter = converter;
        mCallback = callback;
    }

    public Converter getConverter() {
        return mConverter;
    }

    public Callback<T> getCallback() {
        return mCallback;
    }

}
