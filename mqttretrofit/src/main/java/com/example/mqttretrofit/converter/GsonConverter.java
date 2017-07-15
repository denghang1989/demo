package com.example.mqttretrofit.converter;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.lang.reflect.Type;

public class GsonConverter<T> implements Converter {

	TypeAdapter adapter;

	public GsonConverter(Gson gson, Type type) {
		adapter = gson.getAdapter(TypeToken.get(type));
	}

	public T convert(String json) throws IOException {
		return (T) adapter.fromJson(json);
	}
}
