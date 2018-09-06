package com.saintfine.customer.test.xiaoxigetest;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.saintfine.customer.test.R;

/**
 * @author by zhuxiaoan on 2018/9/6 0006.
 */

public class XiaoxigeOneAndHeadFuseView extends LinearLayout {

    private Context mContext;
    private XiaoxigeOneView mXiaoxigeOneView;
    private ImageView mIvHead;

    private int mIvHeaderHeight;


    public XiaoxigeOneAndHeadFuseView(Context context) {
        this(context, null);
    }

    public XiaoxigeOneAndHeadFuseView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XiaoxigeOneAndHeadFuseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOrientation(LinearLayout.HORIZONTAL);
        this.mContext = context;

        initViews();
        registerListener();
    }

    private void initViews() {
        View.inflate(mContext, R.layout.view_xiaoxige_one_head_fuse, this);
        mXiaoxigeOneView = (XiaoxigeOneView) getChildAt(0);
        mIvHead = (ImageView) getChildAt(1);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mIvHeaderHeight <= 0) {
            mIvHeaderHeight = mIvHead.getMeasuredHeight();
        }
    }

    private void registerListener() {
        mXiaoxigeOneView.setProgressListener(new XiaoxigeOneView.OnProgressListener() {
            @Override
            public void progressChanged(View v, float progress) {
//                Log.e("TAG", "progress = " + progress + ", " + mIvHeaderHeight);
                progress = 1f - progress;
                progress = Math.min(Math.max(0.3f, progress), 1f);
                int size = (int) (mIvHeaderHeight * progress);
                ViewGroup.LayoutParams params = mIvHead.getLayoutParams();
                if (params == null) {
                    params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                }
                params.width = size;
                params.height = size;
                mIvHead.setLayoutParams(params);
            }
        });
    }

}
