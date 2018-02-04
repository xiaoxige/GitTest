package in.srain.cube.ultra.loadmore.recylerview;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;


/**
 * Created by dzq on 15/9/10.
 */
public class LoadMoreRecylerContainer extends LoadMoreContainerRecylerBase {
    private RecyclerView mListView;

    public LoadMoreRecylerContainer(Context context) {
        super(context);
    }

    public LoadMoreRecylerContainer(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected boolean addFooterView(View var1) {
        final RecyclerView.Adapter adapter = mListView.getAdapter();

        RecyclerView.LayoutManager layoutManager = mListView.getLayoutManager();
        if (layoutManager != null) {
            final GridLayoutManager manager = (GridLayoutManager) layoutManager;
            manager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (adapter == null) {
                        return 1;
                    }
                    int itemCount = adapter.getItemCount();
                    if (itemCount <= 0 || itemCount == position + 1) {
                        return manager.getSpanCount();
                    }
                    return 1;
                }
            });
        }

        if (adapter != null) {
            ILoadMoreAdapter mAdapter = (ILoadMoreAdapter) adapter;
            mAdapter.addFooterView(var1);
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected boolean removeFooterView(View var1) {
        RecyclerView.Adapter adapter = mListView.getAdapter();
        if (adapter != null) {
            ILoadMoreAdapter mAdapter = (ILoadMoreAdapter) adapter;
            mAdapter.removeFooterView(var1);
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected RecyclerView retrieveAbsListView() {
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = this.getChildAt(i);
            if (view instanceof RecyclerView) {
                mListView = (RecyclerView) view;
                return mListView;
            } else if (view instanceof ViewGroup) {
                int childCount = ((ViewGroup) view).getChildCount();
                for (int k = 0; k < childCount; k++) {
                    View childView = ((ViewGroup) view).getChildAt(k);
                    if (childView instanceof RecyclerView) {
                        mListView = (RecyclerView) childView;
                        return mListView;
                    }
                }
            }
        }
        return this.mListView;
    }

}
