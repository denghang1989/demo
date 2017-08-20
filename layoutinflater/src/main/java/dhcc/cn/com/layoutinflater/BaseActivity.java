package dhcc.cn.com.layoutinflater;

import android.os.Bundle;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        LayoutInflaterCompat.setFactory(LayoutInflater.from(this), new SkinLayoutInflaterFactory(this));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
