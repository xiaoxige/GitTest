package in.srain.cube.ultra.loadmore.recylerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.LinearLayout;

/**
 * Created by dzq on 15/9/10.
 * 加载更多的adapter基类
 */
public abstract class LoadMoreRecylerViewAdapter extends RecyclerView.Adapter implements ILoadMoreAdapter {
    public static final int TYPE_FOOTER = 67;
    public static final int TYPE_CONTENT = 66;
    private View mFooterView;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_FOOTER) {
            LinearLayout layout = new LinearLayout(parent.getContext());
            layout.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new FooterViewHolder(layout);
        } else {
            return onCreateHolder(parent, viewType);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_FOOTER) {
            if (mFooterView != null) {
                ViewGroup parent = (ViewGroup) mFooterView.getParent();
                if (parent != null) {
                    parent.removeView(mFooterView);
                }
                ((FooterViewHolder) holder).contentLayout.addView(mFooterView);
            }
        } else {
            onBindHolder(holder, position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return TYPE_FOOTER;
        } else {
            return getItemType(position);
        }
    }

    @Override
    public View getFooterView() {
        return mFooterView;
    }

    @Override
    public void addFooterView(View view) {
        this.mFooterView = view;
        notifyItemChanged(getItemCount() - 1);
    }

    @Override
    public void removeFooterView(View view) {
        this.mFooterView = null;
        notifyItemChanged(getItemCount() - 1);
    }

    @Override
    public int getItemCount() {
        return getCount() + 1;
    }

    class FooterViewHolder extends RecyclerView.ViewHolder {
        LinearLayout contentLayout;

        public FooterViewHolder(View itemView) {
            super(itemView);
            contentLayout = (LinearLayout) itemView;
        }
    }

    public abstract RecyclerView.ViewHolder onCreateHolder(ViewGroup parent, int viewType);

    public abstract void onBindHolder(RecyclerView.ViewHolder holder, int position);

    public abstract int getCount();

    public int getItemType(int position) {
        return TYPE_CONTENT;
    }
}
