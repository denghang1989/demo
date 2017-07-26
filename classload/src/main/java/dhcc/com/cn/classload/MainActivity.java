package dhcc.com.cn.classload;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.File;
import java.lang.ref.WeakReference;

import dalvik.system.DexClassLoader;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private MainHandler mHandler = new MainHandler(this);

    private static class MainHandler extends Handler {
        private WeakReference<MainActivity> mWeakReference;

        public MainHandler(MainActivity activity) {
            mWeakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            MainActivity activity = mWeakReference.get();

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String absolutePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.pathSeparator + "kotlin.jar";
        File optimizedDexOutputPath = new File(absolutePath);
        File dexOutputDir = this.getDir("dex", 0);
        DexClassLoader dexClassLoader = new DexClassLoader(dexOutputDir.getAbsolutePath(), optimizedDexOutputPath.getAbsolutePath(), null, getClassLoader());

        try {
            Class<?> loadClass = dexClassLoader.loadClass("cn.com.dhcc.Test");
            Log.d(TAG, "onCreate: " + loadClass.getCanonicalName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}
