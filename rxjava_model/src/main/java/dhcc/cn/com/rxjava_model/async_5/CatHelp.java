package dhcc.cn.com.rxjava_model.async_5;

import android.net.Uri;

import java.util.Collections;
import java.util.List;

/**
 * 2017/8/21 23
 */
public class CatHelp {
    ApiWrapper mApiWrapper;

    public AsyncJob<Uri> saveTheCutenessCat(final String query) {
        AsyncJob<List<Cat>> listAsyncJob = mApiWrapper.queryCats(query);
        final AsyncJob<Cat> catAsyncJob = listAsyncJob.map(new Func<List<Cat>, Cat>() {
            @Override
            public Cat call(List<Cat> cats) {
                return findCutenessCat(cats);
            }
        });
        AsyncJob<Uri> asyncJob = catAsyncJob.flatMap(new Func<Cat, AsyncJob<Uri>>() {
            @Override
            public AsyncJob<Uri> call(Cat cat) {
                return mApiWrapper.saveCutenessCat(cat);
            }
        });
        return asyncJob;
    }

    private Cat findCutenessCat(List<Cat> cats) {
        return Collections.max(cats);
    }
}
