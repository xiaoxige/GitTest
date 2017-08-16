package cn.xiaoxige.vlayoutdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;

/**
 * Created by 小稀革 on 2017/8/16.
 */

public class MyAdapter5 extends DelegateAdapter.Adapter {

    private Context mContext;
    private LayoutHelper mHelper;
    private LayoutInflater mInflater;

    public MyAdapter5(Context context, LayoutHelper layoutHelper) {
        mContext = context;
        mHelper = layoutHelper;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.item_5, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 1;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
