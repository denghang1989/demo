package dhcc.cn.com.material_design;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

/**
 * 2017/8/16 10
 */
public class ObservableActivity extends AppCompatActivity {
    private static final String TAG = "ObservableActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_observable);

        Button button = (Button) findViewById(R.id.button5);
        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                float x = motionEvent.getRawX();
                float y = motionEvent.getRawY();
                Log.d(TAG, "onTouch: "+ x+";Y:"+y);
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        view.setX(x);
                        view.setY(y);
                    break;
                }
                return true;
            }
        });
    }
}
