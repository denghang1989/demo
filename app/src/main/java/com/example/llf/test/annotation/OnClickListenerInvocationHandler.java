package com.example.llf.test.annotation;

import android.app.Activity;

import java.lang.ref.WeakReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author denghang
 * @version V1.0
 * @Package com.example.llf.test.annotation
 * @Description: (用一句话描述该文件做什么)
 * @date 2017/6/2 13
 */
public class OnClickListenerInvocationHandler implements InvocationHandler {
    private WeakReference<Activity> mWeakReference;

    private HashMap<String, Method> mHashMap = new HashMap<>();

    public OnClickListenerInvocationHandler(Activity activity) {
        mWeakReference = new WeakReference<>(activity);
    }

    public void addMethod(String s, Method method) {
        mHashMap.put(s, method);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Activity activity = mWeakReference.get();
        if (activity != null) {
            String name = method.getName();
            Method realMethod = mHashMap.get(name);
            if (realMethod != null) {
                realMethod.invoke(activity, args);
            }
        }
        return null;
    }
}
