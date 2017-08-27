package dhcc.cn.com.constraintlayout_animation;

import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView mTextView;
    private boolean  firstFource;
    private Point<Float, Float> mStartPoint = new Point<>();
    private Point<Float, Float> mEndPoint   = new Point<>(0f, 0f);
    private Point<Float, Float> mCurrentPoint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaseTypeEvaluator baseTypeEvaluator = new BaseTypeEvaluator();
        final ValueAnimator valueAnimator = ValueAnimator.ofObject(baseTypeEvaluator, mStartPoint, mEndPoint);
        valueAnimator.setDuration(500);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                Point<Float, Float> animatedValue = (Point<Float, Float>) valueAnimator.getAnimatedValue();
                mTextView.setX(animatedValue.first);
                mTextView.setY(animatedValue.second);
            }
        });

        mTextView = (TextView) findViewById(R.id.textview);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                valueAnimator.start();
            }
        });
    }

    private class BaseTypeEvaluator implements TypeEvaluator<Point<Float, Float>> {

        @Override
        public Point<Float, Float> evaluate(float t, Point<Float, Float> startValue, Point<Float, Float> endValue) {
            float x = (int) ((1 - t) * (1 - t) * startValue.first + 2 * t * (1 - t) * mCurrentPoint.first + t * t * endValue.first);
            float y = (int) ((1 - t) * (1 - t) * startValue.second + 2 * t * (1 - t) * mCurrentPoint.second + t * t * endValue.second);
            return new Point<>(x, y);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && !firstFource) {
            mStartPoint.first = mTextView.getX();
            mStartPoint.second = mTextView.getY();
            mCurrentPoint = new Point<>(0f, mStartPoint.second / 2);
            firstFource = !firstFource;
        }
    }

    private class Point<T, S> {
        public T first;
        public S second;

        public Point() {
        }

        public Point(T first, S second) {
            this.first = first;
            this.second = second;
        }
    }
}
