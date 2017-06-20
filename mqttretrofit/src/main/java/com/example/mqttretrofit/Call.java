package com.example.mqttretrofit;

/**
 * @date 2017/6/20 10
 */
public interface Call<T> extends Cloneable{

    void enqueue(Callback<T> callback);

}
