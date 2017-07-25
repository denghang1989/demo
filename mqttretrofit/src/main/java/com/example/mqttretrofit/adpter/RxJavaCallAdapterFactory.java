package com.example.mqttretrofit.adpter;


import com.example.mqttretrofit.Call;
import com.example.mqttretrofit.R;
import com.example.mqttretrofit.utlis.Utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import rx.Observable;
import rx.Subscriber;

/**
 * 2017/7/20 23
 */
public class RxJavaCallAdapterFactory extends CallAdapter.Factory {
    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations) {
        if (getRawType(returnType) != Observable.class) {
            return null;
        }
        Type responseType = Utils.getCallResponseType(returnType);
        return new SimpleCallAdapter(responseType);
    }

    private static final class SimpleCallAdapter implements CallAdapter<Object, Observable<R>> {
        private Type responseType;

        public SimpleCallAdapter(Type responseType) {
            this.responseType = responseType;
        }

        @Override
        public Type responseType() {
            return responseType;
        }

        @Override
        public Observable<R> adapt(Call<Object> call) {
            return Observable.create(new Observable.OnSubscribe<R>() {
                @Override
                public void call(Subscriber<? super R> subscriber) {
                    subscriber.onNext();
                }
            });
        }

    }
}
