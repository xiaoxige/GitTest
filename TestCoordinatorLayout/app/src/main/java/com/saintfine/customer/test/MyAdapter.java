package com.saintfine.customer.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author by zhuxiaoan on 2018/7/16 0016.
 */

public class MyAdapter extends RecyclerView.Adapter {

    private Context context;

    public MyAdapter(Context context) {

        this.context = context;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.list_item_test, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).setData(position);
        ((ViewHolder) holder).setListener(position);
    }

    @Override
    public int getItemCount() {
        return 20;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        public void setData(int position) {
            ((TextView) ((ViewGroup) itemView).getChildAt(1)).setText(position + "");
        }

        public void setListener(final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(context, "position = " + position, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
