package com.example.mqttretrofit.converter;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by kkmike999 on 2016/06/05.
 */
public class GsonConverterFactory extends Converter.Factory {

    Gson gson;

    public GsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    public GsonConverterFactory() {
        gson = new Gson();
    }

    @Override
    public Converter getConverter(Type type) {
        return new GsonConverter<>(gson, type);
    }
}
