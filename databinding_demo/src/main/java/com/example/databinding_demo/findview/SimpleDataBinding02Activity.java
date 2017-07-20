package com.example.databinding_demo.findview;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.example.databinding_demo.R;
import com.example.databinding_demo.SimpleTestDataBinding;

/**
 * @date 2017/6/12 09
 */
public class SimpleDataBinding02Activity extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SimpleTestDataBinding testDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_simple_data_binding_02);

        testDataBinding.button8.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Toast.makeText(this, "自定义类名称", Toast.LENGTH_SHORT).show();
    }
}
