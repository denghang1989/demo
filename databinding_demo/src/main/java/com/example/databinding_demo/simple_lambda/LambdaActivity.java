package com.example.databinding_demo.simple_lambda;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.databinding_demo.R;
import com.example.databinding_demo.databinding.ActivityLambdaBinding;

/**
 * 2017/8/18 12
 */
public class LambdaActivity extends AppCompatActivity {
    ActivityLambdaBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_lambda);
        mBinding.setStudent(new Student("hehe", 18));
        mBinding.setModel(new ViewModel());
    }
}
