package com.example.mqttretrofit.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

/**
 * @author denghang
 * @version V1.0
 * @Package com.example.mqttretrofit.converter
 * @Description: (用一句话描述该文件做什么)
 * @date 2017/7/20 09
 */
public class GsonResponseBodyConverter<T> implements Converter<String, T> {
    private final Gson mGson;
    private final TypeAdapter<T> mAdapter;

    public GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        mGson = gson;
        mAdapter = adapter;
    }

    @Override
    public T convert(String value) throws IOException {
        return mAdapter.fromJson(value);
    }
}
