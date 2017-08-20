package dhcc.cn.com.material_design;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.view.View;

/**
 * 2017/8/16 10
 */
public class ObservableBehavior extends CoordinatorLayout.Behavior<View> {

    private int mDependentId;

    public ObservableBehavior() {
        this(null,null);
    }

    public ObservableBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ObservableBehavior);
        mDependentId = ta.getResourceId(R.styleable.ObservableBehavior_ob_dependentId, 0);
        ta.recycle();
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        return dependency.getId() == mDependentId;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        child.setX(dependency.getX());
        child.setY(dependency.getY()+dependency.getMeasuredHeight());
        return true;
    }
}
