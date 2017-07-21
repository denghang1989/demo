package com.example.mqttretrofit.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

public class GsonConverterFactory extends Converter.Factory {

    private final Gson gson;

    public GsonConverterFactory(Gson gson) {
        if (gson == null) {
            throw new NullPointerException("gson == null");
        }
        this.gson = gson;
    }

    public GsonConverterFactory() {
        gson = new Gson();
    }

    @Override
    public Converter<?, String> requestBodyConverter(Type type, Annotation[] annotations, Annotation[] methodAnnotations) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonRequestBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<String, ?> responseBodyConverter(Type type, Annotation[] parameterAnnotations) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonResponseBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<?, String> stringConverter(Type type, Annotation[] annotations) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new GsonRequestBodyConverter<>(gson, adapter);
    }

    @Override
    public String mapToString(Map<String, String> map) {
        return gson.toJson(map);
    }
}
