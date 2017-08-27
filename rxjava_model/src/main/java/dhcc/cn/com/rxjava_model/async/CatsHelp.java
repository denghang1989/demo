package dhcc.cn.com.rxjava_model.async;

import android.net.Uri;

import java.util.Collections;
import java.util.List;

/**
 * 2017/8/21 22
 */
public class CatsHelp {
    Api mApi;

    interface CutestCatCallback {
        void onCutestCatSave(Uri uri);

        void onError(Exception e);
    }

    public void saveTheCutenessCat(String query, final CutestCatCallback callback) {
        mApi.queryCats(query, new Api.AsyncCallback() {
            @Override
            public void onListCatReceived(List<Cat> cats) {
                Cat cat = findCutenessCat(cats);
                Uri uri = mApi.store(cat);
                callback.onCutestCatSave(uri);
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
