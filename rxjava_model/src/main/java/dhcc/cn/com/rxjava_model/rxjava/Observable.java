package dhcc.cn.com.rxjava_model.rxjava;

/**
 * 2017/8/22 18
 */
public class Observable<T> {

    private OnSubscrible<T> mOnSubscrible;

    private Observable(OnSubscrible<T> subscrible) {
        mOnSubscrible = subscrible;
    }

    public static <T> Observable<T> create(OnSubscrible<T> onSubscrible) {
        return new Observable<>(onSubscrible);
    }

    public void subscrible(Subscriber<T> subscriber) {
        mOnSubscrible.call(subscriber);
    }
}
