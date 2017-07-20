package com.example.databinding_demo.simple_image;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.databinding_demo.R;
import com.example.databinding_demo.databinding.ActivitySimpleImageBinding;

/**
 * @date 2017/6/12 11
 */
public class SimpleImageActivity extends AppCompatActivity {

    private static final String KEY_URL = "http://avatar.csdn.net/4/9/8/1_a10615.jpg";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySimpleImageBinding imageBinding = DataBindingUtil.setContentView(this, R.layout.activity_simple_image);
        imageBinding.setImageUrl(KEY_URL);
    }
}
