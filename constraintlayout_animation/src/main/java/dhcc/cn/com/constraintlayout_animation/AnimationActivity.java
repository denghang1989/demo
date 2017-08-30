package dhcc.cn.com.constraintlayout_animation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * 2017/8/27 21
 */
public class AnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ConstraintLayout constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        final ConstraintSet constraintSet1 = new ConstraintSet();
        constraintSet1.clone(this, R.layout.activity_main_animation);
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TransitionManager.beginDelayedTransition(constraintLayout);
                constraintSet1.applyTo(constraintLayout);
            }
        });
    }
}
