package cn.xiaoxige.loadmoretest;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import in.srain.cube.ultra.loadmore.recylerview.LoadMoreRecylerContainer;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;

/**
 * Created by zhuxiaoan on 2018/2/4.
 */

public abstract class LoadMorePtrHandler implements PtrHandler {

    @Override
    public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
        LoadMoreRecylerContainer loadMoreLayout = (LoadMoreRecylerContainer) frame.getChildAt(0);
        RecyclerView recyclerView = (RecyclerView) loadMoreLayout.getChildAt(0);
//        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
//        return layoutManager.findFirstCompletelyVisibleItemPosition() == 0;
        return !recyclerView.canScrollVertically(-1);
    }

}
