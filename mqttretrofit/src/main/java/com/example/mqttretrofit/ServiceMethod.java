package com.example.mqttretrofit;

import com.example.mqttretrofit.annotation.Cmd;
import com.example.mqttretrofit.annotation.Topic;
import com.example.mqttretrofit.utlis.Utils;

import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @date 2017/6/20 11
 */
public class ServiceMethod {
    private Method method;
    private Object[] args;
    private String cmd;
    private String topic;
    protected Type actualType; //MqttCall<?> 里面的type

    public ServiceMethod(Builder builder) {
        this.method = builder.method;
        this.cmd = builder.cmd;
        this.topic = builder.topic;
        this.actualType = builder.actualType;
        this.args = builder.args;
    }

    public MqttMessage getMessage() {
        // TODO: 2017/7/14  处理参数 返回json对象
        MqttMessage mqttMessage = new MqttMessage();
        for (int i = 0; i < args.length; i++) {
            if (args[i] instanceof String) {
                mqttMessage.setPayload(((String) args[i]).getBytes());
            }
        }
        return mqttMessage;
    }

    public static final class Builder {
        final Method method;
        final Object[] args;
        final Annotation[] methodAnnotations;
        final Annotation[][] parameterAnnotationsArray;
        protected Type actualType;
        protected Type returnType;
        private String cmd;
        private String topic;


        public Builder(Method method, Object[] args) {
            this.method = method;
            this.args = args;
            this.methodAnnotations = method.getAnnotations();
            this.parameterAnnotationsArray = method.getParameterAnnotations();
            this.returnType = method.getGenericReturnType();
            if (returnType instanceof ParameterizedType) {
                Type[] arguments = ((ParameterizedType) returnType).getActualTypeArguments();
                actualType = arguments[0];
            } else {
                actualType = returnType;
            }
        }

        public ServiceMethod build() {
            for (Annotation methodAnnotation : methodAnnotations) {
                parseMethodAnnotation(methodAnnotation);
            }
            if (!(returnType instanceof Call)) {

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
