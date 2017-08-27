package dhcc.cn.com.rxjava_model.async_5;

import android.net.Uri;

import java.util.List;

/**
 * 2017/8/21 23
 */
public interface Api {
    interface QueryCatsCallback {
        void onCatListReceiver(List<Cat> cats);

        void onError(Exception e);
    }

    interface SaveCatCallback {
        void onSaveCatReceiver(Uri uri);

        void onError(Exception e);
    }

    void queryCats(String query, QueryCatsCallback queryCatsCallback);

    void saveCat(Cat cat, SaveCatCallback saveCatCallback);

}
