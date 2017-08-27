package dhcc.cn.com.rxjava_model.async_5;


/**
 * 2017/8/21 23
 */
public abstract class AsyncJob<T> {
    public abstract void start(Callback<T> callback);

    public <R> AsyncJob<R> map(final Func<T, R> func) {
        final AsyncJob<T> asyncJob = this;
        return new AsyncJob<R>() {
            @Override
            public void start(final Callback<R> callback) {
                asyncJob.start(new Callback<T>() {
                    @Override
                    public void onSuccess(T t) {
                        R call = func.call(t);
                        callback.onSuccess(call);
                    }

                    @Override
                    public void onError(Exception e) {
                        callback.onError(e);
                    }
                });
            }
        };
    }

    public <R> AsyncJob<R> flatMap(final Func<T, AsyncJob<R>> func) {
        final AsyncJob<T> asyncJob = this;
        return new AsyncJob<R>() {
            @Override
            public void start(final Callback<R> callback) {
                asyncJob.start(new Callback<T>() {
                    @Override
                    public void onSuccess(T t) {
                        AsyncJob<R> call = func.call(t);
                        call.start(new Callback<R>() {
                            @Override
                            public void onSuccess(R r) {
                                callback.onSuccess(r);
                            }

                            @Override
                            public void onError(Exception e) {
                                callback.onError(e);
                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {

                    }
                });
            }
        };

    }
}
