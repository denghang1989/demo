package com.example.mqttretrofit;

/**
 * @date 2017/6/20 10
 */
public interface Callback<T> {

    void onSuccess(T t);

    void onError(Throwable t);
}
