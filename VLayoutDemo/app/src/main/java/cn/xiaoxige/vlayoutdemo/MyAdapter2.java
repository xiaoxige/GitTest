package cn.xiaoxige.vlayoutdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;

import java.util.List;

/**
 * Created by 小稀革 on 2017/7/16.
 */

public class MyAdapter2 extends DelegateAdapter.Adapter {

    private Context mContext;
    private List<String> mDatas;
    private LayoutHelper helper;

    public MyAdapter2(Context context, LayoutHelper helper) {
        mContext = context;
        this.helper = helper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_2, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return helper;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
