package dhcc.cn.com.rxjava_model.sync;

import android.net.Uri;

import java.util.Collections;
import java.util.List;

/**
 * 2017/8/21 22
 */
public class CatsHelp {
    Api mApi;

    public Uri saveCutenessCat(String query) {
        Uri uri = null;
        try {
            List<Cat> cats = mApi.queryCats(query);
            Cat cat = findCutenessCat(cats);
            uri = mApi.store(cat);
            return uri;
        } catch (Exception e) {
            e.printStackTrace();
            return uri;
        }
    }

    private Cat findCutenessCat(List<Cat> cats) {
        return Collections.max(cats);
    }


}
