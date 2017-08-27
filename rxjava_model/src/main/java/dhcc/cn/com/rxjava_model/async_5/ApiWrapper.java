package dhcc.cn.com.rxjava_model.async_5;

import android.net.Uri;

import java.util.List;

/**
 * 2017/8/21 23
 */
public class ApiWrapper {
    Api mApi;

    public AsyncJob<List<Cat>> queryCats(final String query) {
        return new AsyncJob<List<Cat>>() {
            @Override
            public void start(final Callback<List<Cat>> callback) {
                mApi.queryCats(query, new Api.QueryCatsCallback() {
                    @Override
                    public void onCatListReceiver(List<Cat> cats) {
                        callback.onSuccess(cats);
                    }

                    @Override
                    public void onError(Exception e) {
                        callback.onError(e);
                    }
                });
            }
        };

    }

    public AsyncJob<Uri> saveCutenessCat(final Cat cat) {
        return new AsyncJob<Uri>() {
            @Override
            public void start(final Callback<Uri> callback) {
                mApi.saveCat(cat, new Api.SaveCatCallback() {
                    @Override
                    public void onSaveCatReceiver(Uri uri) {
                        callback.onSuccess(uri);
                    }

                    @Override
                    public void onError(Exception e) {
                        callback.onError(e);
                    }
                });
            }
        };
    }

}
