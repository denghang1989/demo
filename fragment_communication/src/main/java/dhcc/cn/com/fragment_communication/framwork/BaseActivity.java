package dhcc.cn.com.fragment_communication.framwork;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * 2017/8/16 17
 */
public class BaseActivity extends AppCompatActivity {
    private FunctionsManager mFunctionsManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFunctionsManager = FunctionsManager.getInstance();
        registerFunctions(mFunctionsManager);
    }

    public FunctionsManager getFunctionsManager() {
        return mFunctionsManager;
    }

    protected void registerFunctions(FunctionsManager functionsManager) {

    }
}
