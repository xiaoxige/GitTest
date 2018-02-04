package in.srain.cube.ultra.loadmore.recylerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import in.srain.cube.ultra.loadmore.R;
import in.srain.cube.ultra.loadmore.utils.ProgressWheel;


/**
 * Created by dzq on 15/9/10.
 */
public class LoadMoreRecylerFooterView extends RelativeLayout implements LoadMoreRecylerUIHandler {
    private TextView mTextView;
    private ProgressWheel progress;
    public LoadMoreRecylerFooterView(Context context) {
        this(context, (AttributeSet)null);
    }

    public LoadMoreRecylerFooterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadMoreRecylerFooterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.setupViews();
    }

    private void setupViews() {
        LayoutInflater.from(this.getContext()).inflate(R.layout.layout_loadingmore, this);
        this.mTextView = (TextView)this.findViewById(R.id.loading_text);
        this.progress = (ProgressWheel)this.findViewById(R.id.progress_wheel);
    }

    public void onLoading(LoadMoreContainerRecyler container) {
        this.setVisibility(VISIBLE);
        this.progress.setVisibility(View.VISIBLE);
        this.mTextView.setText(R.string.cube_views_load_more_loading);
    }

    public void onLoadFinish(LoadMoreContainerRecyler container, boolean empty, boolean hasMore) {
        this.setVisibility(VISIBLE);
//        if(!hasMore) {
            if(empty) {
                this.progress.setVisibility(View.GONE);
                this.mTextView.setText(R.string.cube_views_load_more_loaded_empty);
            } else {
                this.progress.setVisibility(View.GONE);
                this.mTextView.setText(R.string.cube_views_load_more_loaded_no_more);
            }
//        } else {
//            this.setVisibility(VISIBLE);
//        }

    }

    public void onWaitToLoadMore(LoadMoreContainerRecyler container) {
        this.setVisibility(VISIBLE);
        this.progress.setVisibility(View.GONE);
        this.mTextView.setText(R.string.cube_views_load_more_click_to_load_more);
    }

    public void onLoadError(LoadMoreContainerRecyler container, int errorCode, String errorMessage) {
        this.progress.setVisibility(View.GONE);
        this.mTextView.setText(R.string.cube_views_load_more_error);
    }
}
