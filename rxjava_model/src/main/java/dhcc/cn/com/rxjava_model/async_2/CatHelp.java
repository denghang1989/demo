package dhcc.cn.com.rxjava_model.async_2;

import android.net.Uri;

import java.util.Collections;
import java.util.List;

/**
 * 2017/8/21 23
 */
public class CatHelp {
    Api mApi;

    interface CutestCatCallback {
        void onCutestCatSave(Uri uri);

        void onError(Exception e);
    }

    public void saveTheCutenessCat(String query, final CutestCatCallback cutestCatCallback) {
        mApi.queryCats(query, new Api.QueryCatsCallback() {
            @Override
            public void onCatListReceiver(List<Cat> cats) {
                Cat cat = findCutenessCat(cats);
                mApi.saveCat(cat, new Api.SaveCatCallback() {
                    @Override
                    public void onSaveCatReceiver(Uri uri) {
                        cutestCatCallback.onCutestCatSave(uri);
                    }

                    @Override
                    public void onError(Exception e) {
                        cutestCatCallback.onError(e);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                cutestCatCallback.onError(e);
            }
        });
    }

    private Cat findCutenessCat(List<Cat> cats) {
        return Collections.max(cats);
    }
}
