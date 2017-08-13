package dhcc.cn.com.auto_layout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        double v = Math.sqrt(1920 * 1920 + 1080 * 1080) / 72;
        Log.d(TAG, "onCreate: " + v);
    }
}
