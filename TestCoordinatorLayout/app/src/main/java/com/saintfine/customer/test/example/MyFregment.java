package com.saintfine.customer.test.example;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saintfine.customer.test.MyAdapter;
import com.saintfine.customer.test.MyItemDecoration;
import com.saintfine.customer.test.R;

/**
 * @author by zhuxiaoan on 2018/7/16 0016.
 */

public class MyFregment extends Fragment {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fregment_example, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        myAdapter = new MyAdapter(getContext());

        recyclerView.addItemDecoration(new MyItemDecoration());

        recyclerView.setAdapter(myAdapter);

        return view;
    }
}
