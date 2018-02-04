package cn.xiaoxige.loadmoretest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import in.srain.cube.ultra.loadmore.recylerview.LoadMoreContainerRecyler;
import in.srain.cube.ultra.loadmore.recylerview.LoadMoreRecylerContainer;
import in.srain.cube.ultra.loadmore.recylerview.LoadMoreRecylerHandler;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrFrameLayout;

public class MainActivity extends Activity {

    private RecyclerView recyclerView;
    private PtrClassicFrameLayout refreshLayout;
    private LoadMoreRecylerContainer loadMoreLayout;
    private MyAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initData();
        registerListener();
    }

    private void registerListener() {
        refreshLayout.setPtrHandler(new LoadMorePtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.refreshComplete();
                    }
                }, 2000);
            }
        });

        loadMoreLayout.setLoadMoreHandler(new LoadMoreRecylerHandler() {
            @Override
            public void onLoadMore(LoadMoreContainerRecyler var1) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        loadMoreLayout.loadMoreFinish(false, true);
                    }
                }, 2000);
            }
        });
    }

    private void initData() {

    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        refreshLayout = (PtrClassicFrameLayout) findViewById(R.id.refreshLayout);
        loadMoreLayout = (LoadMoreRecylerContainer) findViewById(R.id.loadMoreLayout);
        mAdapter = new MyAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(mAdapter);
    }
}