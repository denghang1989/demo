package dhcc.cn.com.fragment_communication.framwork;

import android.content.Context;
import android.support.v4.app.Fragment;

/**
 * 2017/8/16 17
 */
public class BaseFragment extends Fragment {
    private BaseActivity mBaseActivity;
    protected FunctionsManager mFunctionsManager;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mBaseActivity = (BaseActivity) context;
    }

    public void setFunctionsManager(FunctionsManager functionsManager) {
        mFunctionsManager = functionsManager;
    }
}
