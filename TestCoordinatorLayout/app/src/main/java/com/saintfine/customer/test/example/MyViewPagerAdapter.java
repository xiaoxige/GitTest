package com.saintfine.customer.test.example;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * @author by zhuxiaoan on 2018/7/16 0016.
 */

public class MyViewPagerAdapter extends FragmentPagerAdapter {

    private List<String> title;
    private List<Fragment> fragments;

    private MyViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    public MyViewPagerAdapter(FragmentManager manager, List<String> title, List<Fragment> fragments) {
        this(manager);
        this.title = title;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return Math.min(this.title.size(), this.fragments.size());
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return this.title.get(position);
    }
}
