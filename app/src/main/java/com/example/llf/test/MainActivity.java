package com.example.llf.test;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DataBindingUtil.setContentView(this, R.layout.activity_main);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button30:
                startJumpActivity(HLinearLayoutActivity.class);
                break;
            case R.id.button31:
                startJumpActivity(VLinearLayoutActivity.class);
                break;
            case R.id.button32:
                startJumpActivity(BaselineActivity.class);
                break;
            case R.id.button33:
                startJumpActivity(BiasActivity.class);
                break;
            case R.id.button34:
                startJumpActivity(ChainsActivity.class);
                break;
            case R.id.button35:
                startJumpActivity(GoneActivity.class);
                break;
            case R.id.button36:
                startJumpActivity(GuidelineActivity.class);
                break;
            case R.id.button37:
                startJumpActivity(RelativeLayoutActivity.class);
                break;
            case R.id.button38:
                break;
            case R.id.button39:
                break;
            case R.id.button40:
                break;
            case R.id.button41:
                break;
            case R.id.button42:
                break;
            case R.id.button43:
                break;
            case R.id.button44:
                break;
            case R.id.button45:
                break;
            case R.id.button46:
                break;
            case R.id.button47:
                break;
        }
    }

    private void startJumpActivity(Class<? extends Activity> activity) {
        startActivity(new Intent(this, activity));
    }
}
