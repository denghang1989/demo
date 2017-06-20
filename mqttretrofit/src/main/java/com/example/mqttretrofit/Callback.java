package com.example.mqttretrofit;

/**
 * @date 2017/6/20 10
 */
public interface Callback<T> {

    void onResponse(T t);

    void onFailure(Throwable t);
}
