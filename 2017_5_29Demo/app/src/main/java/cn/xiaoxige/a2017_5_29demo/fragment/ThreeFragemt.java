package cn.xiaoxige.a2017_5_29demo.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import cn.xiaoxige.a2017_5_29demo.BaseFragment;
import cn.xiaoxige.a2017_5_29demo.NumAdapter;
import cn.xiaoxige.a2017_5_29demo.R;

/**
 * Created by 小稀革 on 2017/5/29.
 */

public class ThreeFragemt extends BaseFragment {

    private RecyclerView recyclerView;
    private List<String> mInfos;
    private NumAdapter numAdapter;
    private Toolbar toolbar;

    @Override
    protected void initListener() {
    }

    @Override
    protected void initParams() {
        mInfos = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            mInfos.add("小稀革" + i);
        }
    }


    @Override
    protected void initData() {
        numAdapter = new NumAdapter(context, mInfos);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(numAdapter);
    }

    @Override
    protected View getLayout() {
        View view = View.inflate(context, R.layout.fragment_three, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        toolbar = (Toolbar) view.findViewById(R.id.toolbar);
        toolbar.setTitle("小稀革");
        return view;
    }
}
