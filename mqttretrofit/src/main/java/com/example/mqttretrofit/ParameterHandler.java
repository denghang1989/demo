package com.example.mqttretrofit;

import android.support.annotation.Nullable;

import com.example.mqttretrofit.converter.Converter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;


/**
 * @author denghang
 * @version V1.0
 * @Package com.example.mqttretrofit
 * @Description: (用一句话描述该文件做什么)
 * @date 2017/7/20 11
 */
abstract class ParameterHandler<T> {
    abstract void apply(Map<String, String> stringMap, @Nullable T value);

    static final class Query<T> extends ParameterHandler<T> {
        private final String name;
        private final Converter<T, String> valueConverter;

        public Query(String name, Converter<T, String> converter) {
            this.name = name;
            valueConverter = converter;
        }

        @Override
        void apply(Map<String, String> stringMap, @Nullable T value) {
            if (value == null)
                return;

            String queryValue = null;
            try {
                queryValue = valueConverter.convert(value);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (queryValue == null) {
                return;
            }
            stringMap.put(name, queryValue);
        }
    }

    static final class QueryMap<T> extends ParameterHandler<Map<String, T>> {
        public QueryMap() {
        }

        @Override
        void apply(Map<String, String> stringMap, @Nullable Map<String, T> value) {
            if (value == null) {
                throw new IllegalArgumentException("Query map was null.");
            }

            for (Map.Entry<String, T> entry : value.entrySet()) {
                String entryKey = entry.getKey();
                if (entryKey == null) {
                    throw new IllegalArgumentException("Query map contained null key.");
                }
                T entryValue = entry.getValue();
                if (entryValue == null) {
                    throw new IllegalArgumentException(
                            "Query map contained null value for key '" + entryKey + "'.");
                }
                stringMap.put(entryKey, (String) entryValue);
            }
        }
    }

    static final class Body<T> extends ParameterHandler<T> {
        private Converter<T, String> mConverter;

        public Body(Converter<T, String> converter) {
            mConverter = converter;
        }

        @Override
        void apply(Map<String, String> stringMap, @Nullable T value) {
            if (value == null) {
                throw new IllegalArgumentException("Body parameter value must not be null.");
            }
            try {
                String convert = mConverter.convert(value);
                JSONObject jsonObject = new JSONObject(convert);
                Iterator<String> keys = jsonObject.keys();
                while (keys.hasNext()) {
                    String keyName = keys.next();
                    String valueName = jsonObject.optString(keyName);
                    stringMap.put(keyName, valueName);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
