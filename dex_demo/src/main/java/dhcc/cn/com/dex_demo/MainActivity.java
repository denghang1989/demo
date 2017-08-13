package dhcc.cn.com.dex_demo;

import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File externalStorageDirectory = Environment.getExternalStorageDirectory();
        File optimiDexPath = new File(externalStorageDirectory, "out.jar");

        try {
            DexClassLoader dex = new DexClassLoader(optimiDexPath.getAbsolutePath(), getFilesDir().getAbsolutePath(), null, getClassLoader());
            Class<?> loadClass = dex.loadClass("com.fgecctv.Test");
            Method[] methods = loadClass.getMethods();
            for (Method method : methods) {
                Log.d(TAG, "onCreate: "+ method.getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /*final DexClassLoader dexClassLoader = new DexClassLoader(optimiDexPath.getAbsolutePath(),
                dexOutputDir.getAbsolutePath(),
                null,
                getClassLoader());

        mTextView = (TextView) findViewById(R.id.textView);
        mTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Class<?> loadClass = dexClassLoader.loadClass("cn.com.dhcc.demo.Test");
                    Object o = loadClass.newInstance();
                    Method method = loadClass.getDeclaredMethod("say", null);
                    method.invoke(o);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        });
    }*/
    }
}
