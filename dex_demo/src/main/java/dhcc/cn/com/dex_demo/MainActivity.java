package dhcc.cn.com.dex_demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

/*        File optimiDexPath = new File(Environment.getExternalStorageDirectory(), "plugin.apk");
        if (optimiDexPath.exists()) {
            Log.d(TAG, "onCreate: "+ optimiDexPath.length());
        }
        File appDir = getDir("plugin", Context.MODE_PRIVATE);

        try {
            DexClassLoader dex = new DexClassLoader(optimiDexPath.getAbsolutePath(), appDir.getAbsolutePath(), null, getClassLoader());
            Class<?> loadClass = dex.loadClass("com.android.tools.fd.runtime.Logging");
            Method[] methods = loadClass.getMethods();
            for (Method method : methods) {
                Log.d(TAG, "onCreate: "+ method.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }*/

    }
}
