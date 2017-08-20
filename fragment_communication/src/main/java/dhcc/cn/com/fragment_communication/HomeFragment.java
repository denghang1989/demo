package dhcc.cn.com.fragment_communication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import dhcc.cn.com.fragment_communication.framwork.BaseFragment;

/**
 * 2017/8/16 18
 */
public class HomeFragment extends BaseFragment {

    public static final String KEY_HOME_FRAGMENT = HomeFragment.class.getName() + "fragment";
    private Button mButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mButton = view.findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mFunctionsManager.invokeFunc(KEY_HOME_FRAGMENT);
            }
        });
    }
}
