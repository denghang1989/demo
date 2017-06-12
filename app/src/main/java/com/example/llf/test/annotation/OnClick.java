package com.example.llf.test.annotation;

import android.view.View;

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
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Listener(classType = View.OnClickListener.class, methodName = "onClick", setOnListenerName = "setOnClickListener")
public @interface OnClick {
    int[] value();
}
