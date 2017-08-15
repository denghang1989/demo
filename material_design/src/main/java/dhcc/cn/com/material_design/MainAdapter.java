package dhcc.cn.com.material_design;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 2017/8/13 16
 */
public class MainAdapter extends FragmentPagerAdapter {

    private String[] mTitles = new String[]{"scroll", "recyclerView", "webView"};

    public MainAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new ScrollFragment();
                break;
            case 1:
                fragment = new RecyclerViewFragment();
                break;
            case 2:
                fragment = new WebViewFragment();
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return mTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles[position];
    }
}
