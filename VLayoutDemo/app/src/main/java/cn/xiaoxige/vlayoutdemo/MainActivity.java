package cn.xiaoxige.vlayoutdemo;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;

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

        adapters = new ArrayList<>();
        adapters.add(new MyAdapter1(this, new LinearLayoutHelper()));
        adapters.add(new MyAdapter2(this, new GridLayoutHelper(3)));

        adapter.addAdapters(adapters);

    }

}
