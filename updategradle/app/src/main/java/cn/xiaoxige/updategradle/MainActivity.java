package cn.xiaoxige.updategradle;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        recyclerView = findViewById(R.id.recyclerView);

        myAdapter = new MyAdapter(this);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setLayoutManager(new MyLayoutManager(this));
        recyclerView.setAdapter(myAdapter);

        recyclerView.post(new Runnable() {
            @Override
            public void run() {
                Log.e("TAG", "" + recyclerView.getMeasuredHeight());
                int childCount = recyclerView.getChildCount();
                Log.e("TAG", "" + childCount);


            }
        });

    }
}
