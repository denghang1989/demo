package dhcc.cn.com.plugin_didi;

import android.app.Application;
import android.content.Context;

import com.didi.virtualapk.PluginManager;

/**
 * 2017/8/20 18
 */
public class App extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        PluginManager.getInstance(base).init();
    }
}
