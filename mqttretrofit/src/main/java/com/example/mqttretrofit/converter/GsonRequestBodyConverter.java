package com.example.mqttretrofit.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

/**
 * @author denghang
 * @version V1.0
 * @Package com.example.mqttretrofit.converter
 * @Description: (用一句话描述该文件做什么)
 * @date 2017/7/20 09
 */
public class GsonRequestBodyConverter<T> implements Converter<T, String> {
    private final Gson mGson;
    private final TypeAdapter<T> mAdapter;

    public GsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        mGson = gson;
        mAdapter = adapter;
    }

    @Override
    public String convert(T value) {
        return mAdapter.toJson(value);
    }
}
