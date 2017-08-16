package cn.xiaoxige.vlayoutdemo;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.alibaba.android.vlayout.layout.ScrollFixLayoutHelper;
import com.alibaba.android.vlayout.layout.SingleLayoutHelper;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;

    private List<DelegateAdapter.Adapter> adapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        VirtualLayoutManager manager = new VirtualLayoutManager(this);

        recyclerView.setLayoutManager(manager);

        DelegateAdapter adapter = new DelegateAdapter(manager, false);

        recyclerView.setAdapter(adapter);


        MyAdapter4 myAdapter4 = new MyAdapter4(this, new SingleLayoutHelper());
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(4);
        gridLayoutHelper.setBgColor(Color.GREEN);
        gridLayoutHelper.setAutoExpand(false);
        gridLayoutHelper.setPadding(60, 0, 60, 0);

        ScrollFixLayoutHelper scrollFixLayoutHelper = new ScrollFixLayoutHelper(ScrollFixLayoutHelper.TOP_LEFT, 30, 30);

        adapters = new ArrayList<>();
        adapters.add(myAdapter4);
        adapters.add(new MyAdapter1(this, new LinearLayoutHelper()));
        adapters.add(new MyAdapter1(this, new LinearLayoutHelper()));
        adapters.add(new MyAdapter1(this, new LinearLayoutHelper()));
        adapters.add(new MyAdapter2(this, gridLayoutHelper));
        adapters.add(new MyAdapter5(this, scrollFixLayoutHelper));
        adapter.addAdapters(adapters);

    }

}
