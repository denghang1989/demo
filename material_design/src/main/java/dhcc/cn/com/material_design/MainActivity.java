package dhcc.cn.com.material_design;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    /*UI*/
    private CoordinatorLayout       mCoordinatorLayout;
    private AppBarLayout            mAppBarLayout;
    private CollapsingToolbarLayout mCollapsingToolbarLayout;
    private ViewPager               mViewPager;
    private Toolbar                 mToolbar;
    private TabLayout               mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initListener() {
        mAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset >= appBarLayout.getMeasuredHeight() / 2) {
                    mCollapsingToolbarLayout.setTitle("hehe");
                } else {
                    mCollapsingToolbarLayout.setTitle("haha");
                }
            }
        });
    }

    private void initView() {
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorLayout);
        mAppBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        mCollapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbarLayout);
        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        initViewPager(mViewPager, mTabLayout);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        initToolbar(mToolbar);
    }

    private void initViewPager(ViewPager viewPager, TabLayout tabLayout) {
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new MainAdapter(getSupportFragmentManager()));
    }

    private void initToolbar(Toolbar toolbar) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
