package com.example.databinding_demo.simple_animator;

import android.databinding.DataBindingUtil;
import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.view.ViewGroup;

import com.example.databinding_demo.R;
import com.example.databinding_demo.databinding.ActivityAnimatorBinding;

/**
 * 2017/8/18 12
 */
public class AnimatorActivity extends AppCompatActivity {
    ActivityAnimatorBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_animator);
        mBinding.addOnRebindCallback(new OnRebindCallback() {
            @Override
            public boolean onPreBind(ViewDataBinding binding) {
                ViewGroup viewGroup = (ViewGroup) binding.getRoot();
                TransitionManager.beginDelayedTransition(viewGroup);
                return true;
            }
        });
        mBinding.setModel(new ViewModel(mBinding));
        mBinding.setShow(true);
    }


}
