package com.example.llf.test.annotation;

import android.app.Activity;
import android.view.View;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author denghang
 * @version V1.0
 * @Package com.example.llf.test.annotation
 * @Description: (IOC 测试demo)
 * @date 2017/5/31 20
 */
public class AnntationUtil {

    public static void inject(Activity activity) {
        injectView(activity);
        injectListener(activity);
    }

    private static void injectView(Activity activity) {
        Field[] fields = activity.getClass().getFields();
        for (Field field : fields) {
            boolean present = field.isAnnotationPresent(BindView.class);
            if (present) {
                BindView bindView = field.getAnnotation(BindView.class);
                int id = bindView.value();
                View view = activity.findViewById(id);
                try {
                    field.setAccessible(true);
                    field.set(activity, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void injectListener(Activity activity) {
        Method[] methods = activity.getClass().getMethods();
        for (Method method : methods) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> type = annotation.annotationType();
                Listener listener = type.getAnnotation(Listener.class);
                if (listener != null) {
                    Class<?> clazz = listener.classType();
                    String methodName = listener.methodName();
                    String setOnListenerName = listener.setOnListenerName();
                    OnClickListenerInvocationHandler handler = new OnClickListenerInvocationHandler(activity);
                    handler.addMethod(methodName, method);
                    Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, handler);
                    try {
                        Method declaredMethod = type.getDeclaredMethod("value");
                        int[] viewId = (int[]) declaredMethod.invoke(annotation, (Object) null);

                        for (int i : viewId) {
                            View view = activity.findViewById(i);
                            Method setOnListener = view.getClass().getMethod(setOnListenerName, clazz);
                            setOnListener.invoke(view, o);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
