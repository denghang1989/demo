package dhcc.cn.com.rxjava_model.async_4;

public interface Callback<T> {
        void onSuccess(T t);

        void onError(Exception e);
    }