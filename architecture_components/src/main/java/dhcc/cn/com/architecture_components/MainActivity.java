package dhcc.cn.com.architecture_components;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import dhcc.cn.com.architecture_components.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    private ActivityMainBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        initToolbar(mBinding.toolbarMain);
    }

    private void initToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("");
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        return true;
    }
}
