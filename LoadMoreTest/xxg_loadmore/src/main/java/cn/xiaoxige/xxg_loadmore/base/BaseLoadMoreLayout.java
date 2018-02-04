package cn.xiaoxige.xxg_loadmore.base;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by zhuxiaoan on 2018/2/4.
 * 顶层加载更多
 */

public class BaseLoadMoreLayout extends FrameLayout {

    protected View mView;
    protected int mViewHeight;

    public BaseLoadMoreLayout(@NonNull Context context) {
        this(context, null);
    }

    public BaseLoadMoreLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseLoadMoreLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount != 1) {
            throw new RuntimeException("loadmore must have a child");
        }
        mView = getChildAt(0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = MeasureSpec.getSize(heightMeasureSpec);
        mViewHeight = size;
    }
}
