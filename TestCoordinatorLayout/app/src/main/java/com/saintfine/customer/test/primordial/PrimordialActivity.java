package com.saintfine.customer.test.primordial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;

import com.saintfine.customer.test.R;
import com.saintfine.customer.test.example.MyFregment;
import com.saintfine.customer.test.example.MyViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by zhuxiaoan on 2018/7/17 0017.
 */

public class PrimordialActivity extends FragmentActivity {

    private AppBarLayout appBarLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FrameLayout flTitleContailer;

    private MyViewPagerAdapter myViewPagerAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primordial);


        appBarLayout = (AppBarLayout) findViewById(R.id.appBarLayout);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        flTitleContailer = (FrameLayout) findViewById(R.id.flTitleContailer);


        List<String> titles = new ArrayList<>();
        titles.add("小稀革");
        titles.add("朱肖安");

        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new MyFregment());
        fragments.add(new MyFregment());

        myViewPagerAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), titles, fragments);
        viewPager.setAdapter(myViewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int measuredHeight = appBarLayout.getChildAt(0).getMeasuredHeight();
                int measuredHeight1 = flTitleContailer.getMeasuredHeight();
                int canScrollheight = measuredHeight - measuredHeight1;
                verticalOffset = Math.abs(verticalOffset);

                float v = (float) verticalOffset / canScrollheight;
                flTitleContailer.getChildAt(0).setAlpha(v);
            }
        });


    }

}
