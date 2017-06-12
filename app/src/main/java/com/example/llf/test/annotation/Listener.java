package com.example.llf.test.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author denghang
 * @version V1.0
 * @Package com.example.llf.test.annotation
 * @Description: (用一句话描述该文件做什么)
 * @date 2017/5/31 20
 */
@Target(ElementType.ANNOTATION_TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Listener {
    Class<?> classType();

    String methodName();

    String setOnListenerName();
}
