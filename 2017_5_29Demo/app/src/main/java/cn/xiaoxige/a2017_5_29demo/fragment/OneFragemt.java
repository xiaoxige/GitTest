package cn.xiaoxige.a2017_5_29demo.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.xiaoxige.a2017_5_29demo.BaseFragment;
import cn.xiaoxige.a2017_5_29demo.OnePagerAdapter;
import cn.xiaoxige.a2017_5_29demo.R;

import static android.support.design.widget.TabLayout.MODE_SCROLLABLE;

/**
 * Created by 小稀革 on 2017/5/29.
 */

public class OneFragemt extends BaseFragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private OnePagerAdapter adapter;
    private List<BaseFragment> fragments;

    @Override
    protected void initParams() {

    }

    @Override
    protected View getLayout() {
        View view = View.inflate(context, R.layout.fragment_one, null);
        tabLayout = (TabLayout) view.findViewById(R.id.tabLayout);
        viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        return view;
    }

    @Override
    protected void initData() {
        fragments = new ArrayList<>();
        fragments.add(new OneOneragemt());
        fragments.add(new TwoTwofragemt());
        fragments.add(new ThreeThreefragemt());
        fragments.add(new Fivefragemt());
        fragments.add(new TwoTwofragemt());
        fragments.add(new ThreeThreefragemt());
        fragments.add(new OneOneragemt());
        fragments.add(new TwoTwofragemt());
        fragments.add(new ThreeThreefragemt());
        fragments.add(new OneOneragemt());
        fragments.add(new TwoTwofragemt());
        fragments.add(new ThreeThreefragemt());
        adapter = new OnePagerAdapter(getChildFragmentManager(), context, fragments);
        viewPager.setAdapter(adapter);
        tabLayout.setTabMode(MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void initListener() {

    }
}
