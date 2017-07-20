package com.example.mqttretrofit.converter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Map;

public interface Converter<F, T> {
    T convert(F value) throws IOException;

    abstract class Factory {
        public Converter<String, ?> responseBodyConverter(Type type, Annotation[] parameterAnnotations) {
            return null;
        }

        public Converter<?, String> requestBodyConverter(Type type, Annotation[] annotations, Annotation[] methodAnnotations) {
            return null;
        }

        public Converter<?, String> stringConverter(Type type, Annotation[] annotations) {
            return null;
        }

        public String mapToString(Map<String, String> map) {
            return null;
        }
    }
}
