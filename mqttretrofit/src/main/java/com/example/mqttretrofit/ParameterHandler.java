package com.example.mqttretrofit;

import android.support.annotation.Nullable;

import com.example.mqttretrofit.converter.Converter;

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
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

    final ParameterHandler<Iterable<T>> iterable() {
        return new ParameterHandler<Iterable<T>>() {
            @Override
            void apply(Map<String, String> stringMap, @Nullable Iterable<T> values) {
                if (values == null)
                    return; // Skip null values.

                for (T value : values) {
                    ParameterHandler.this.apply(stringMap, value);
                }
            }
        };
    }

    final ParameterHandler<Object> array() {
        return new ParameterHandler<Object>() {
            @Override
            void apply(Map<String, String> stringMap, @Nullable Object values) {
                if (values == null)
                    return; // Skip null values.

                for (int i = 0, size = Array.getLength(values); i < size; i++) {
                    //noinspection unchecked
                    ParameterHandler.this.apply(stringMap, (T) Array.get(values, i));
                }
            }
        };
    }


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
                return; // Skip null values.

            String queryValue = null;
            try {
                queryValue = valueConverter.convert(value);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (queryValue == null)
                return; // Skip converted but null values

        }
    }

    static final class QueryMap<T> extends ParameterHandler<Map<String, T>> {

        public QueryMap() {
        }

        @Override
        void apply(Map<String, String> builder, @Nullable Map<String, T> value) {
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

               /* String convertedEntryValue = valueConverter.convert(entryValue);
                if (convertedEntryValue == null) {
                    throw new IllegalArgumentException("Query map value '"
                            + entryValue
                            + "' converted to null by "
                            + valueConverter.getClass().getName()
                            + " for key '"
                            + entryKey
                            + "'.");
                }

                builder.addQueryParam(entryKey, convertedEntryValue, encoded);*/
            }
        }
    }

    static final class Body<T> extends ParameterHandler<T> {
        private final Converter<T, String> converter;
        private final Type t;

        public Body(Type t, Converter<T, String> converter) {
            this.converter = converter;
            this.t = t;
        }

        @Override
        void apply(Map<String, String> stringMap, @Nullable T value) {
            if (value == null) {
                throw new IllegalArgumentException("Body parameter value must not be null.");
            }
            try {
                String c = converter.convert(value);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}
