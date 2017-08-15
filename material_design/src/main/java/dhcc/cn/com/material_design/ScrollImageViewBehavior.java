package dhcc.cn.com.material_design;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * 2017/8/14 15
 */
public class ScrollImageViewBehavior extends CoordinatorLayout.Behavior<ImageView> implements AppBarLayout.OnOffsetChangedListener {
    private static final String TAG = "ScrollImageViewBehavior";

    private int                 mDependentViewID;
    private int                 mOffsetY;
    private int                 mTargetHeight;
    private int                 mTargetwidth;
    private int                 mTargetX;
    private int                 mTargetY;
    private boolean             isFirst;
    private WeakReference<View> dependencyView;
    private WeakReference<View> childView;
    private CoordinatorLayout   mCoordinatorLayout;
    private ImageView           mImageView;

    private int   dependencyHeight;
    private int   childHeight;
    private int   childWidth;
    private float childX;
    private float childY;
    private float childMarginTop;

    /**
     * Appbar  被拉出来 拉下来 的进度， 全拉出来时  为  100
     */
    private float progress;

    public ScrollImageViewBehavior() {
        this(null, null);
    }

    public ScrollImageViewBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);

        dependencyHeight = -1;
        childHeight = -1;
        childWidth = -1;
        childX = -1;
        childY = -1;

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.scrollImageViewBehavior);
        mDependentViewID = typedArray.getResourceId(R.styleable.scrollImageViewBehavior_bh_dependentViewId, -1);
        mOffsetY = typedArray.getDimensionPixelSize(R.styleable.scrollImageViewBehavior_bh_offsetY, 0);
        mTargetHeight = typedArray.getDimensionPixelSize(R.styleable.scrollImageViewBehavior_bh_targetHeight, 0);
        mTargetwidth = typedArray.getDimensionPixelSize(R.styleable.scrollImageViewBehavior_bh_targetWidth, 0);
        mTargetX = typedArray.getDimensionPixelSize(R.styleable.scrollImageViewBehavior_bh_targetX, 0);
        mTargetY = typedArray.getDimensionPixelSize(R.styleable.scrollImageViewBehavior_bh_targetY, 0);
        typedArray.recycle();
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, ImageView child, View dependency) {
        mCoordinatorLayout = mCoordinatorLayout == null ? parent : mCoordinatorLayout;
        mImageView = mImageView == null ? child : mImageView;
        return true;
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, ImageView child, View dependency) {
        boolean isHeadView = dependency.getId() == mDependentViewID;
        if (dependency instanceof AppBarLayout && !isFirst) {
            isFirst = true;
            ((AppBarLayout) dependency).addOnOffsetChangedListener(this);
        }
        ViewCompat.setPivotX(child, 0);
        ViewCompat.setPivotY(child, 0);
        dependencyView = dependencyView == null ? new WeakReference<>(dependency) : dependencyView;
        if (isHeadView) {
            childView = childView == null ? new WeakReference<>(dependency) : childView;
        }
        return isHeadView;
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        if (mCoordinatorLayout == null || mImageView == null) {
            return;
        }
        onAppOffsetChanged(-verticalOffset);
    }

    private void onAppOffsetChanged(int verticalOffset) {
        if (getDependentView() == null || mCoordinatorLayout == null || mImageView == null) {
            return;
        }
        dependencyHeight = dependencyHeight < 0 ? getDependentView().getMeasuredHeight() : dependencyHeight;
        childWidth = childWidth < 0 ? mImageView.getMeasuredWidth() : childWidth;
        childHeight = childHeight < 0 ? mImageView.getMeasuredHeight() : childHeight;
        childX = childX < 0 ? mImageView.getX() : childX;
        childY = childY < 0 ? mImageView.getY() : childY;
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mImageView.getLayoutParams();
        childMarginTop = childMarginTop < 0 ? params.topMargin : childMarginTop;

        float progress = (dependencyHeight - verticalOffset - mOffsetY) * 1.0f / (dependencyHeight - mOffsetY);

        // 比例大小
        float scale = mTargetHeight * 1.0f / childHeight;
        float progressScale = scale + (1 - scale) * progress;
        ViewCompat.setScaleX(mImageView, progressScale);
        ViewCompat.setScaleY(mImageView, progressScale);
        // translate
        float newX = mTargetX + (childX - mTargetX) * progress;
        float newY = mTargetY + (childY - mTargetY) * progress;
        ViewCompat.setX(mImageView, newX);
        ViewCompat.setY(mImageView, newY);
        // marginTop
        params.topMargin = (int) (childMarginTop * progress);
        mImageView.setLayoutParams(params);
    }

    public View getDependentView() {
        return dependencyView.get();
    }

    @Override
    public boolean onLayoutChild(CoordinatorLayout parent, ImageView child, int layoutDirection) {
        int width = parent.getMeasuredWidth();
        child.layout(width / 2 - child.getMeasuredWidth() / 2, mOffsetY, width / 2 + child.getMeasuredWidth() / 2, mOffsetY + child.getMeasuredHeight());
        return true;
    }
}
