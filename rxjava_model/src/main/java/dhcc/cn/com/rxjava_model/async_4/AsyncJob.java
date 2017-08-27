package dhcc.cn.com.rxjava_model.async_4;


/**
 * 2017/8/21 23
 */
public abstract class AsyncJob<T> {
    public abstract void start(Callback<T> callback);
}
