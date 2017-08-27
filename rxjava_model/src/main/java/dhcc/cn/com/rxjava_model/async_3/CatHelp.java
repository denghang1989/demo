package dhcc.cn.com.rxjava_model.async_3;

import android.net.Uri;

import java.util.Collections;
import java.util.List;

/**
 * 2017/8/21 23
 */
public class CatHelp {
    Api mApi;

    public void saveTheCutenessCat(String query, final Api.Callback<Uri> callback) {
        mApi.queryCats(query, new Api.Callback<List<Cat>>() {
            @Override
            public void onSuccess(List<Cat> cats) {
                Cat cat = findCutenessCat(cats);
                mApi.saveCutestCat(cat, new Api.Callback<Uri>() {
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

    private Cat findCutenessCat(List<Cat> cats) {
        return Collections.max(cats);
    }
}
