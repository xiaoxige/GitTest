package in.srain.cube.ultra.loadmore.recylerview;

/**
 * Created by dzq on 15/9/10.
 */
public interface LoadMoreRecylerUIHandler {
    void onLoading(LoadMoreContainerRecyler var1);

    void onLoadFinish(LoadMoreContainerRecyler var1, boolean var2, boolean var3);

    void onWaitToLoadMore(LoadMoreContainerRecyler var1);

    void onLoadError(LoadMoreContainerRecyler var1, int var2, String var3);
}
