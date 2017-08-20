package dhcc.cn.com.fragment_communication;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;

import dhcc.cn.com.fragment_communication.framwork.BaseActivity;
import dhcc.cn.com.fragment_communication.framwork.BaseFragment;
import dhcc.cn.com.fragment_communication.framwork.FunctionNoParamNoResult;
import dhcc.cn.com.fragment_communication.framwork.FunctionsManager;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaseFragment fragment = new HomeFragment();
        fragment.setFunctionsManager(getFunctionsManager());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout,fragment);
        transaction.commit();

    }

    @Override
    protected void registerFunctions(FunctionsManager functionsManager) {
        functionsManager.addFunction(new FunctionNoParamNoResult(HomeFragment.KEY_HOME_FRAGMENT) {
            @Override
            public void invoke() {
                Toast.makeText(MainActivity.this, "haha", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
