package com.example.llf.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * @date 2017/6/9 17  虚拟帮助对象，目前最新版1.1.0-beta1有Guideline、Barrier、Group三种对象。既然是虚拟对象，那么它们就不占用实际的空间。
 */
public class BarrierActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barrier);
    }
}
