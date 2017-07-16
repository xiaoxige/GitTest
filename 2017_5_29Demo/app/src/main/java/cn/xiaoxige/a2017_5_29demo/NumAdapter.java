package cn.xiaoxige.a2017_5_29demo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by 小稀革 on 2017/5/30.
 */

public class NumAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<String> mInfos;

    public NumAdapter(Context context, List<String> infos) {
        mContext = context;
        mInfos = infos;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_num, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String item = mInfos.get(position);
        ((ViewHolder) holder).setData(item);
    }

    @Override
    public int getItemCount() {
        return mInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_num;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_num = (TextView) itemView.findViewById(R.id.tv_num);
        }

        public void setData(String num) {
            tv_num.setText(num);
        }
    }
}
