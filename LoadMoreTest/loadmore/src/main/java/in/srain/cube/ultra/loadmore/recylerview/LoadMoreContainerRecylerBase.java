package in.srain.cube.ultra.loadmore.recylerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Created by dzq on 15/9/10.
 */
public abstract class LoadMoreContainerRecylerBase extends LinearLayout implements LoadMoreContainerRecyler {
    private RecyclerView.OnScrollListener mOnScrollListener;
    private LoadMoreRecylerUIHandler mLoadMoreUIHandler;
    private LoadMoreRecylerHandler mLoadMoreHandler;
    private boolean mIsLoading;
    private boolean mHasMore = false;
    private boolean mAutoLoadMore = true;
    private boolean mLoadError = false;
    private boolean mListEmpty = true;
    private boolean mShowLoadingForFirstPage = true;
    private View mFooterView;
    private RecyclerView mAbsListView;
    private boolean isAddFooter = false;
    public LoadMoreContainerRecylerBase(Context context) {
        super(context);
    }

    public LoadMoreContainerRecylerBase(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mAbsListView = this.retrieveAbsListView();
        this.useDefaultFooter();
        this.init();
    }
//
//    /** @deprecated */
//    @Deprecated
//    public void useDefaultHeader() {
//        this.useDefaultFooter();
//    }

    public void useDefaultFooter() {
        LoadMoreRecylerFooterView footerView = new LoadMoreRecylerFooterView(this.getContext());
        footerView.setVisibility(GONE);
        this.setLoadMoreView(footerView);
        this.setLoadMoreUIHandler(footerView);
    }

    private void init() {
        if(this.mFooterView != null) {
            isAddFooter = this.addFooterView(this.mFooterView);
        }
        this.mAbsListView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private boolean mIsEnd = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                if (null != LoadMoreContainerRecylerBase.this.mOnScrollListener) {
                    LoadMoreContainerRecylerBase.this.mOnScrollListener.onScrollStateChanged(recyclerView, newState);
                }
                if (newState == 0 && this.mIsEnd) {
                    LoadMoreContainerRecylerBase.this.onReachBottom();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (null != LoadMoreContainerRecylerBase.this.mOnScrollListener) {
                    LoadMoreContainerRecylerBase.this.mOnScrollListener.onScrolled(recyclerView, dx, dy);
                }
                LinearLayoutManager manager = ((LinearLayoutManager) mAbsListView.getLayoutManager());
                if (manager.findLastVisibleItemPosition() >= manager.getItemCount() - 3) {
                    this.mIsEnd = true;
                } else {
                    this.mIsEnd = false;
                }
            }
        });
    }

    private void tryToPerformLoadMore() {
        if(!this.mIsLoading) {
            if(this.mHasMore || this.mListEmpty && this.mShowLoadingForFirstPage) {
                this.mIsLoading = true;
                if(this.mLoadMoreUIHandler != null) {
                    this.mLoadMoreUIHandler.onLoading(this);
                }

                if(null != this.mLoadMoreHandler) {
                    this.mLoadMoreHandler.onLoadMore(this);
                }

            }
        }
    }

    private void onReachBottom() {
        if(!isAddFooter && this.mFooterView != null) {
            isAddFooter = this.addFooterView(this.mFooterView);
        }
        if(!this.mLoadError) {
            if(this.mAutoLoadMore) {
                this.tryToPerformLoadMore();
            } else if(this.mHasMore) {
                this.mLoadMoreUIHandler.onWaitToLoadMore(this);
            }

        }
    }

    public void setShowLoadingForFirstPage(boolean showLoading) {
        this.mShowLoadingForFirstPage = showLoading;
    }

    public void setAutoLoadMore(boolean autoLoadMore) {
        this.mAutoLoadMore = autoLoadMore;
    }

    public void setOnScrollListener(RecyclerView.OnScrollListener l) {
        this.mOnScrollListener = l;
    }

    public void setLoadMoreView(View view) {
        if(this.mAbsListView == null) {
            this.mFooterView = view;
        } else {
            if(this.mFooterView != null && this.mFooterView != view) {
                isAddFooter = !this.removeFooterView(view);
            }

            this.mFooterView = view;
            this.mFooterView.setOnClickListener(new OnClickListener() {
                public void onClick(View view) {
                    LoadMoreContainerRecylerBase.this.tryToPerformLoadMore();
                }
            });
            isAddFooter = this.addFooterView(view);
        }
    }

    public void setLoadMoreUIHandler(LoadMoreRecylerUIHandler handler) {
        this.mLoadMoreUIHandler = handler;
    }

    public void setLoadMoreHandler(LoadMoreRecylerHandler handler) {
        this.mLoadMoreHandler = handler;
    }

    public void loadMoreFinish(boolean emptyResult, boolean hasMore) {
        this.mLoadError = false;
        this.mListEmpty = emptyResult;
        this.mIsLoading = false;
        this.mHasMore = hasMore;
        if(this.mLoadMoreUIHandler != null) {
            this.mLoadMoreUIHandler.onLoadFinish(this, emptyResult, hasMore);
        }

    }

    public void loadMoreError(int errorCode, String errorMessage) {
        this.mIsLoading = false;
        this.mLoadError = true;
        if(this.mLoadMoreUIHandler != null) {
            this.mLoadMoreUIHandler.onLoadError(this, errorCode, errorMessage);
        }

    }
    public void waitLoadMore() {
        if(!isAddFooter && this.mFooterView != null) {
            isAddFooter = this.addFooterView(this.mFooterView);
        }
        this.mLoadError = false;
        this.mListEmpty = false;
        this.mIsLoading = false;
        this.mHasMore = true;
        if(this.mLoadMoreUIHandler != null) {
            this.mLoadMoreUIHandler.onWaitToLoadMore(this);
        }

    }

    protected abstract boolean addFooterView(View var1);

    protected abstract boolean removeFooterView(View var1);

    protected abstract RecyclerView retrieveAbsListView();
}
