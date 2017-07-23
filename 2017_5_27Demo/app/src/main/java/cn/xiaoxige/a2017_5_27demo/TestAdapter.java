package cn.xiaoxige.a2017_5_27demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 小稀革 on 2017/5/28.
 */

public class TestAdapter extends RecyclerView.Adapter {

    private Context mContext;
    private List<String> mItems;

    public TestAdapter(Context mContext, List<String> mItems) {
        this.mContext = mContext;
        this.mItems = mItems;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String item = mItems.get(position);
        ((ViewHolder) holder).setData(item);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvItem;

        public ViewHolder(View itemView) {
            super(itemView);
            tvItem = (TextView) itemView.findViewById(R.id.tvItem);
        }

        public void setData(String item) {
            tvItem.setText(item);
        }
    }

}
