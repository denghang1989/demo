package com.example.mqttretrofit.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author denghang
 * @version V1.0
 * @Package com.example.mqttretrofit.annotation
 * @Description: (用一句话描述该文件做什么)
 * @date 2017/7/21 09
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface CommandName {
    String value();
}
