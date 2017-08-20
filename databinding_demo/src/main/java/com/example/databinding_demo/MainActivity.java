package com.example.databinding_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.databinding_demo.findview.SimpleDataBindingActivity;
import com.example.databinding_demo.simple_animator.AnimatorActivity;
import com.example.databinding_demo.simple_data.SimpleDataActivity;
import com.example.databinding_demo.simple_image.SimpleImageActivity;
import com.example.databinding_demo.simple_lambda.LambdaActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button1:
                startActivity(new Intent(this, SimpleDataBindingActivity.class));
                break;
            case R.id.button2:
                break;
            case R.id.button3:
                startActivity(new Intent(this, SimpleDataActivity.class));
                break;
            case R.id.button4:
                startActivity(new Intent(this, SimpleImageActivity.class));
                break;
            case R.id.button5:
                startActivity(new Intent(this, LambdaActivity.class));
                break;
            case R.id.button6:
                startActivity(new Intent(this, AnimatorActivity.class));
                break;
            case R.id.button7:
                break;
        }
    }
}
