package dhcc.com.cn.router_demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;

/**
 * @author denghang
 * @version V1.0
 * @Package dhcc.com.cn.router_demo
 * @Description: (用一句话描述该文件做什么)
 * @date 2017/7/26 16
 */
@Route(path = "/test/activity")
public class TestActivity extends AppCompatActivity {
    @Autowired(name = "key")
    String value;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);

        Toast.makeText(this, ""+value, Toast.LENGTH_SHORT).show();
    }
}
