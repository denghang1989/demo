package com.example.mqttretrofit.utlis;

/**
 * @date 2017/6/20 16
 */
public class Utils {

    public static <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }
}
