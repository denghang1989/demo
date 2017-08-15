package dhcc.cn.com.material_design;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * 2017/8/15 18
 */
public class FatingActionButtonBehavior extends CoordinatorLayout.Behavior<View> {
    private static final String TAG = "FatingActionButtonBehav";

    private int         mDependentId;
    private float       progress;
    private float       lastProgress;
    private float       lastOffsetY;
    private AnimatorSet mShowAnimatorSet;
    private AnimatorSet mHideAnimatorSet;

    public FatingActionButtonBehavior() {
        super();
    }

    public FatingActionButtonBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.FatingActionButtonBehavior);
        mDependentId = ta.getResourceId(R.styleable.FatingActionButtonBehavior_fa_dependentId, 0);
        ta.recycle();
    }

    @Override
    public boolean layoutDependsOn(CoordinatorLayout parent, View child, View dependency) {
        Log.d(TAG, "layoutDependsOn: ");
        return dependency.getId() == mDependentId;
    }

    @Override
    public boolean onDependentViewChanged(CoordinatorLayout parent, View child, View dependency) {
        Log.d(TAG, "onDependentViewChanged: ");
        offsetChildView(parent, child, dependency);
        autoShowOrHideChildView(parent, child, dependency);
        return true;
    }

    private void autoShowOrHideChildView(CoordinatorLayout parent, View child, View dependency) {
        int height = dependency.getMeasuredHeight();
        int offsetY = -dependency.getTop();
        progress = offsetY * 1.0f / height;
        if (progress >= 0.5f && lastProgress < progress) {
            if (mShowAnimatorSet != null && mShowAnimatorSet.isRunning()) {
                mShowAnimatorSet.cancel();
            }
            Log.d(TAG, "autoShowOrHideChildView: " + "hide");
            hideChild(child);
        } else if (progress < 0.5f && lastProgress > progress) {
            if (mHideAnimatorSet != null && mHideAnimatorSet.isRunning()) {
                mHideAnimatorSet.cancel();
            }
            Log.d(TAG, "autoShowOrHideChildView: " + "show");
            showChild(child);
        }
        lastProgress = progress;
    }

    private void showChild(final View child) {
        if ((mShowAnimatorSet != null && mShowAnimatorSet.isRunning()) || child.getVisibility() == View.VISIBLE) {
            return;
        }
        mShowAnimatorSet = new AnimatorSet();
        mShowAnimatorSet.setDuration(200);
        mShowAnimatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(child, "scaleX", 0, 1f);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(child, "scaleY", 0, 1f);
        mShowAnimatorSet.playTogether(objectAnimatorX, objectAnimatorY);
        mShowAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                child.setVisibility(View.VISIBLE);

            }

            @Override
            public void onAnimationEnd(Animator animation) {
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }
        });
        mShowAnimatorSet.start();
    }

    private void hideChild(final View child) {
        if ((mHideAnimatorSet != null && mHideAnimatorSet.isRunning()) || child.getVisibility() == View.GONE) {
            return;
        }
        mHideAnimatorSet = new AnimatorSet();
        mHideAnimatorSet.setDuration(200);
        mHideAnimatorSet.setInterpolator(new LinearInterpolator());
        ObjectAnimator objectAnimatorX = ObjectAnimator.ofFloat(child, "scaleX", 1f, 0f);
        ObjectAnimator objectAnimatorY = ObjectAnimator.ofFloat(child, "scaleY", 1f, 0f);
        mHideAnimatorSet.playTogether(objectAnimatorX, objectAnimatorY);
        mHideAnimatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                child.setVisibility(View.GONE);
            }
        });
        mHideAnimatorSet.start();
    }

    private void offsetChildView(CoordinatorLayout parent, View child, View dependency) {
        int offsetY = dependency.getTop();
        ViewCompat.setTranslationY(child, offsetY - lastOffsetY);
        lastOffsetY = offsetY;
    }


}
