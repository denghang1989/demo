package dhcc.com.cn.router_demo;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author denghang
 * @version V1.0
 * @Package dhcc.com.cn.router_demo
 * @Description: (用一句话描述该文件做什么)
 * @date 2017/7/26 16
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ARouter.init(this);
    }
}
