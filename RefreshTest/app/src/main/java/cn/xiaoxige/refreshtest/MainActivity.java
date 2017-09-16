package cn.xiaoxige.refreshtest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends Activity {
    private RefreshStickyNavLayout refreshLayout;
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private TextView tvAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initListener();
    }

    private void initListener() {

        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addItem();
            }
        });

        refreshLayout.setOnRefreshListener(new RefreshStickyNavLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.setRefreshComplete();
                    }
                }, 1000);
            }
        });
    }

    private void initView() {
        refreshLayout = (RefreshStickyNavLayout) findViewById(R.id.refreshLayout);
        tvAdd = (TextView) findViewById(R.id.tvAdd);
        adapter = new MyAdapter(this);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
    }
}