package com.example.mqttretrofit;

import com.example.mqttretrofit.annotation.Cmd;
import com.example.mqttretrofit.annotation.Topic;
import com.example.mqttretrofit.utlis.Utils;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @date 2017/6/20 11
 */
public class ServiceMethod {
    private MqttRetrofit mRetrofit;
    private Method method;
    private Annotation[] methodAnnotations;
    private Annotation[][] parameterAnnotationsArray;
    private String cmd;
    private String topic;


    public ServiceMethod(Builder builder) {
        mRetrofit = builder.mRetrofit;
        method = builder.method;
        methodAnnotations = builder.methodAnnotations;
        parameterAnnotationsArray = builder.parameterAnnotationsArray;
        this.cmd = builder.cmd;
        this.topic = builder.topic;
    }


    public MqttMessage getMessage(Object[] args) {
        return null;
    }

    public static final class Builder {
        final MqttRetrofit mRetrofit;
        final Method method;
        final Annotation[] methodAnnotations;
        final Annotation[][] parameterAnnotationsArray;
        private String cmd;
        private String topic;


        public Builder(MqttRetrofit retrofit, Method method) {
            this.mRetrofit = retrofit;
            this.method = method;
            this.methodAnnotations = method.getAnnotations();
            this.parameterAnnotationsArray = method.getParameterAnnotations();
        }

        public ServiceMethod build() {
            for (Annotation methodAnnotation : methodAnnotations) {
                parseMethodAnnotation(methodAnnotation);
            }
            Utils.checkNotNull(cmd, method.getName() + "方法上面缺少@cmd注解");
            Utils.checkNotNull(topic, method.getName() + "方法上面缺少@Topic注解");
            return new ServiceMethod(this);
        }

        private void parseMethodAnnotation(Annotation methodAnnotation) {
            if (methodAnnotation instanceof Cmd) {
                this.cmd = ((Cmd) methodAnnotation).value();
            }

            if (methodAnnotation instanceof Topic) {
                this.topic = ((Topic) methodAnnotation).value();
            }
        }
    }

    public String getCmd() {
        return cmd;
    }

    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }
}
