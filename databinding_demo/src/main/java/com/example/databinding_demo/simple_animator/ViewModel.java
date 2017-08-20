package com.example.databinding_demo.simple_animator;

import android.view.View;

import com.example.databinding_demo.databinding.ActivityAnimatorBinding;

/**
 * 2017/8/18 12
 */
public class ViewModel {
    private ActivityAnimatorBinding mBinding;

    public ViewModel(ActivityAnimatorBinding binding) {
        mBinding = binding;
    }

    public void changed(View view, boolean isChecked){
        mBinding.setShow(isChecked);
    }
}
