package in.srain.cube.ultra.loadmore.recylerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by dzq on 15/9/10.
 */
public interface LoadMoreContainerRecyler {
    void setShowLoadingForFirstPage(boolean var1);

    void setAutoLoadMore(boolean var1);

    void setOnScrollListener(RecyclerView.OnScrollListener var1);

    void setLoadMoreView(View var1);

    void setLoadMoreUIHandler(LoadMoreRecylerUIHandler var1);

    void setLoadMoreHandler(LoadMoreRecylerHandler var1);

    void loadMoreFinish(boolean var1, boolean var2);

    void loadMoreError(int var1, String var2);
}
