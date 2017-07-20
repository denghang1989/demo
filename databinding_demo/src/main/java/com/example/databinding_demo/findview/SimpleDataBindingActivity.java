package com.example.databinding_demo.findview;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.databinding_demo.R;
import com.example.databinding_demo.databinding.ActivitySimpleDataBindingBinding;

/**
 * @date 2017/6/12 09   R.layout.activity_simple_data_binding ----> ActivitySimpleDataBindingBinding
 * @desc 默认情况下 根据布局文件名称生成对应的类名称
 */
public class SimpleDataBindingActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivitySimpleDataBindingBinding mBindingBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBindingBinding = DataBindingUtil.setContentView(this, R.layout.activity_simple_data_binding);
        mBindingBinding.buttonSimple.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "databinding is simple", Toast.LENGTH_SHORT).show();
    }
}
