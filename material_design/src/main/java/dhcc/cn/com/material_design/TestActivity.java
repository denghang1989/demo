package dhcc.cn.com.material_design;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import dhcc.cn.com.material_design.databinding.ActivityTestBinding;

/**
 * 2017/8/14 15
 */
public class TestActivity extends AppCompatActivity implements View.OnClickListener {

    private ActivityTestBinding mDataBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.activity_test);
        mDataBinding.button.setOnClickListener(this);
        mDataBinding.button2.setOnClickListener(this);
        mDataBinding.button3.setOnClickListener(this);
        mDataBinding.button4.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.button:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.button2:
                startActivity(new Intent(this, ScrollImageViewActivity.class));
                break;
            case R.id.button3:
                startActivity(new Intent(this, FatingActionButtonActivity.class));
                break;
            case R.id.button4:
                startActivity(new Intent(this,ObservableActivity.class));
                break;
        }
    }
}
