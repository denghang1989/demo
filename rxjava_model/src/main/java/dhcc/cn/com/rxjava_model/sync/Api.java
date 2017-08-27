package dhcc.cn.com.rxjava_model.sync;


import android.net.Uri;

import java.util.List;

/**
 * 2017/8/21 22
 */
public interface Api {
    List<Cat> queryCats(String query);

    Uri store(Cat cat);
}
