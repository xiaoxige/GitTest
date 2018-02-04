package cn.xiaoxige.loadmoretest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import in.srain.cube.ultra.loadmore.recylerview.LoadMoreRecylerViewAdapter;

/**
 * Created by zhuxiaoan on 2018/2/4.
 */

public class MyAdapter extends LoadMoreRecylerViewAdapter {

    private Context mContext;
    private LayoutInflater mInflater;

    public MyAdapter(Context context) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(mInflater.inflate(R.layout.list_item_test, parent, false));
    }

    @Override
    public void onBindHolder(RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bindData(position);
    }

    @Override
    public int getCount() {
        return 21;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvMsg;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMsg = (TextView) itemView.findViewById(R.id.tvMsg);
        }

        public void bindData(int position) {
            tvMsg.setText(position + "");
        }
    }
}
