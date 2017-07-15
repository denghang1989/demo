package com.example.databinding_demo.simple_data;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.databinding_demo.R;
import com.example.databinding_demo.databinding.ActivitySimpleStudentBinding;

/**
 * @date 2017/6/12 10
 */
public class SimpleDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivitySimpleStudentBinding studentBinding = DataBindingUtil.setContentView(this, R.layout.activity_simple_student);
        studentBinding.setStudent(new Student("jack","家里蹲大学"));
    }
}
