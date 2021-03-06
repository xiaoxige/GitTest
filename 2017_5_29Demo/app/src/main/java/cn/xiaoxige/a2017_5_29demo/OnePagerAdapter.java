package cn.xiaoxige.a2017_5_29demo;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 小稀革 on 2017/5/29.
 */

public class OnePagerAdapter extends FragmentPagerAdapter {

    private String[] title = {
            "one_one", "one_two", "one_three"
    };

    private Context mContext;
    private List<BaseFragment> mFragments;

    public OnePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public OnePagerAdapter(FragmentManager fm, Context context, List<BaseFragment> Fragments) {
        super(fm);
        mContext = context;
        mFragments = Fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position % 3];
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
