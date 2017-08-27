package dhcc.cn.com.rxjava_model.async;

import android.net.Uri;

import java.util.List;

/**
 * 2017/8/21 22
 */
public interface Api {

    interface AsyncCallback {
        void onListCatReceived(List<Cat> cats);

        void onError(Exception e);
    }

    void queryCats(String query, AsyncCallback callback);

    Uri store(Cat cat);
}
