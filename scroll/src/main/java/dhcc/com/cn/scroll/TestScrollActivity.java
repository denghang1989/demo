package dhcc.com.cn.scroll;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * @author denghang
 * @version V1.0
 * @Package dhcc.com.cn.scroll
 * @Description: (用一句话描述该文件做什么)
 * @date 2017/7/27 15
 */
public class TestScrollActivity extends AppCompatActivity {
    private static final String TAG = "TestScrollActivity";

    private TestScrollView mScrollView;
    private TextView mTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mScrollView = (TestScrollView) findViewById(R.id.test_scroll);
        mTextView = (TextView) findViewById(R.id.textView_id);

        Button button = (Button) findViewById(R.id.button3);


        final ValueAnimator valueAnimator = ValueAnimator.ofInt(0, 1).setDuration(1000);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float fraction = animation.getAnimatedFraction();
                mScrollView.scrollTo((int) (100*fraction),0);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewGroup viewgroup = ((ViewGroup) getWindow().getDecorView().findViewById(android.R.id.content));
                for (int i = 0; i < viewgroup.getChildCount(); i++) {
                    View childView = viewgroup.getChildAt(i);
                    Log.d(TAG, "onClick: "+childView.toString());
                }
            }
        });

    }
}
