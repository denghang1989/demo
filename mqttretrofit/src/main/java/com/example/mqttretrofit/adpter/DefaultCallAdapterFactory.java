package com.example.mqttretrofit.adpter;

import com.example.mqttretrofit.Call;
import com.example.mqttretrofit.utlis.Utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * @author denghang
 * @version V1.0
 * @Package com.example.mqttretrofit.adpter
 * @Description: (用一句话描述该文件做什么)
 * @date 2017/7/20 10
 */
public class DefaultCallAdapterFactory extends CallAdapter.Factory {
    public static final CallAdapter.Factory INSTANCE = new DefaultCallAdapterFactory();

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations) {
        if (getRawType(returnType) != Call.class) {
            return null;
        }
        final Type responseType = Utils.getCallResponseType(returnType);
        return new CallAdapter<Object, Call<?>>() {
            @Override
            public Type responseType() {
                return responseType;
            }

            @Override
            public Call<?> adapt(Call<Object> call) {
                return call;
            }
        };
    }
}
