package dhcc.cn.com.rxjava_model.async_5;

public interface Callback<T> {
        void onSuccess(T t);

        void onError(Exception e);
}