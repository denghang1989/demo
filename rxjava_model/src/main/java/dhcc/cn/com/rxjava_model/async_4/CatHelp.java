package dhcc.cn.com.rxjava_model.async_4;

import android.net.Uri;

import java.util.Collections;
import java.util.List;

/**
 * 2017/8/21 23
 */
public class CatHelp {
    ApiWrapper mApiWrapper;

    public AsyncJob<Uri> saveTheCutenessCat(final String query) {
        return new AsyncJob<Uri>() {
            @Override
            public void start(final Callback<Uri> callback) {
                mApiWrapper.queryCats(query).start(new Callback<List<Cat>>() {
                    @Override
                    public void onSuccess(List<Cat> cats) {
                        Cat cat = findCutenessCat(cats);
                        mApiWrapper.saveCutenessCat(cat).start(new Callback<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                callback.onSuccess(uri);
                            }

                            @Override
                            public void onError(Exception e) {
                                callback.onError(e);
                            }
                        });
                    }

                    @Override
                    public void onError(Exception e) {
                        callback.onError(e);
                    }
                });
            }
        };

    }

    private Cat findCutenessCat(List<Cat> cats) {
        return Collections.max(cats);
    }
}
