package dhcc.cn.com.layoutinflater;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * 2017/8/23 10
 */
public class LinearContent extends LinearLayout {
    public LinearContent(Context context) {
        super(context);
    }

    public LinearContent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return super.generateLayoutParams(attrs);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
    }
}
