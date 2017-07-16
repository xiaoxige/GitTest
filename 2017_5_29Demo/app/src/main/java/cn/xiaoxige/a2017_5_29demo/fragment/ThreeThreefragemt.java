package cn.xiaoxige.a2017_5_29demo.fragment;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;

import java.util.ArrayList;
import java.util.List;

import cn.xiaoxige.a2017_5_29demo.BaseFragment;
import cn.xiaoxige.a2017_5_29demo.LoadingLayout;
import cn.xiaoxige.a2017_5_29demo.NumAdapter;
import cn.xiaoxige.a2017_5_29demo.R;
import cn.xiaoxige.a2017_5_29demo.RefreshLayout;

/**
 * Created by 小稀革 on 2017/5/29.
 */

public class ThreeThreefragemt extends BaseFragment {
    private RefreshLayout refreshLayout;
    private RecyclerView recyclerView;
    private List<String> mInfos;
    private NumAdapter numAdapter;
    private LoadingLayout loadingLayout;
    private TextView tvTest;
    @Override
    protected void initListener() {
        refreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(final TwinklingRefreshLayout refreshLayout) {
                super.onRefresh(refreshLayout);
                Toast.makeText(context, "下啦刷新", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishRefreshing();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore(final TwinklingRefreshLayout refreshLayout) {
                super.onLoadMore(refreshLayout);
                Toast.makeText(context, "上拉加载更多", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.finishLoadmore();
                    }
                }, 2000);
            }
        });
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
//        loadingLayout = new LoadingLayout(context, recyclerView);


        numAdapter = new NumAdapter(context, mInfos);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(numAdapter);

        TextView textView = new TextView(context);
        textView.setText("123456789");
        textView.setLayoutParams(new ViewGroup.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));

//        RelativeLayout a = new RelativeLayout(context);
//        a.setLayoutParams(new ViewGroup.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
//        a.addView(textView);

        LinearLayout parent = (LinearLayout) tvTest.getParent();
        parent.removeAllViews();
        parent.addView(textView);
//        loadingLayout.change(BaseLoadingLayout.ERRORPAGER);

    }

    @Override
    protected View getLayout() {
        View view = View.inflate(context, R.layout.fragment_threethree, null);
        refreshLayout = (RefreshLayout) view.findViewById(R.id.refreshLayout);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        tvTest = (TextView) view.findViewById(R.id.tvTest);

        return view;
    }
}
