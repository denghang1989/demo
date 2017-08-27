package dhcc.cn.com.rxjava_model.async;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

/**
 * 2017/8/21 22
 */
public class Cat implements Comparable<Cat> {
    Bitmap mBitmap;
    int    cuteness;

    @Override
    public int compareTo(@NonNull Cat cat) {
        return Integer.compare(cuteness, cat.cuteness);
    }
}
