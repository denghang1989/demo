package dhcc.cn.com.plugin_didi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.didi.virtualapk.PluginManager;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            File file = new File(Environment.getExternalStorageDirectory(),"plugin.apk");
            if (file.exists()) {
                long length = file.length();
                Log.d(TAG, "onCreate: "+length);
            }

            PluginManager.getInstance(this).loadPlugin(file);
            Intent intent = new Intent();
            intent.setClassName("dhcc.cn.com.plugin_demo", "dhcc.cn.com.plugin_demo.MainActivity");
            startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
