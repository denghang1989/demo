package dhcc.cn.com.rxjava_model.async_3;

import android.net.Uri;

import java.util.List;

/**
 * 2017/8/21 23
 */
public interface Api {

    interface Callback<T> {
        void onSuccess(T t);

        void onError(Exception e);
    }

    void queryCats(String query, Callback<List<Cat>> callback);

    void saveCutestCat(Cat cat, Callback<Uri> callback);
}
