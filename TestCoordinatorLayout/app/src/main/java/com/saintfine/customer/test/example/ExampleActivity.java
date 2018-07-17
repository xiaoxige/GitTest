package com.saintfine.customer.test.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.saintfine.customer.test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by zhuxiaoan on 2018/7/16 0016.
 */

public class ExampleActivity extends FragmentActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private MyViewPagerAdapter myViewPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);

        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);


        List<String> titles = new ArrayList<>();
        titles.add("小稀革");
        titles.add("朱肖安");

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MyFregment());
        fragments.add(new MyFregment());

        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(myViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }
}
