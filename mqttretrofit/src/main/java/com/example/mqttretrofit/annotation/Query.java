package com.example.mqttretrofit.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @date 2017/7/14 17
 */
@Target(PARAMETER)
@Retention(RUNTIME)
public @interface Query {
    String value();
}
