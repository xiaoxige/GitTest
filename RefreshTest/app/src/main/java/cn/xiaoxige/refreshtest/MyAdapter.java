package cn.xiaoxige.refreshtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 小稀革 on 2017/9/16.
 */

public class MyAdapter extends RecyclerView.Adapter {
    private Context context;
    private int size = 0;

    public MyAdapter(Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_recyc, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((TextView) ((ViewGroup) ((ViewHolder) holder).itemView).getChildAt(0)).setText("小稀革" + position);
    }

    @Override
    public int getItemCount() {
        return size;
    }

    public void addItem() {
        size++;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
