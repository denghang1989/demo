package dhcc.com.cn.scroll;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.Scroller;

/**
 * @author denghang
 * @version V1.0
 * @Package dhcc.com.cn.scroll
 * @Description: (用一句话描述该文件做什么)
 * @date 2017/7/27 14
 */
public class TestScrollView extends FrameLayout {

    private Scroller mScroller = new Scroller(getContext());

    public TestScrollView(Context context) {
        super(context);
    }

    public TestScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TestScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void smoothScroll(int distX, int distY) {
        int scrollx = getScrollX();
        int deltax = distX - scrollx;
        mScroller.startScroll(deltax, 0, distX, 0, 1000);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
