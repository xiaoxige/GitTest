package cn.xiaoxige.updategradle;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by xiaoxige on 2018/3/11.
 */

public class MyAdapter extends RecyclerView.Adapter {

    private Context mContext;

    public MyAdapter(Context context) {
        this.mContext = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_list_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ViewHolder) holder).bindData(position);
    }

    @Override
    public int getItemCount() {
        return 19;
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvMsg;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMsg = itemView.findViewById(R.id.tvMsg);
        }

        public void bindData(int position) {
            tvMsg.setText(position + "");
        }
    }
}
