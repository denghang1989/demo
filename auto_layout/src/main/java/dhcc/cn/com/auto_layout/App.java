package dhcc.cn.com.auto_layout;

import android.app.Application;
import android.content.res.Configuration;
import android.graphics.Point;
import android.view.WindowManager;

/**
 * 2017/7/28 08
 */
public class App extends Application {
    public static final int DESIGN_WIDTH = 1080;

    @Override
    public void onCreate() {
        super.onCreate();
        resetDensity();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        resetDensity();
    }

    private void resetDensity() {
        Point size = new Point();
        ((WindowManager) getSystemService(WINDOW_SERVICE)).getDefaultDisplay().getSize(size);
        getResources().getDisplayMetrics().xdpi = size.x / DESIGN_WIDTH * 72f;
    }
}
