package dhcc.cn.com.rxjava_model.async_3;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * 2017/8/21 23
 */
public class Cat implements Comparable<Cat> {
    Bitmap mBitmap;

    int mCuteness;

    @Override
    public int compareTo(@NonNull Cat cat) {
        return Integer.compare(mCuteness, cat.mCuteness);
    }
}
