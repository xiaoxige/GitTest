package cn.xiaoxige.xxg_loadmore;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;

import cn.xiaoxige.xxg_loadmore.base.BaseLoadMoreLayout;

/**
 * Created by zhuxiaoan on 2018/2/4.
 * 上拉加载更多
 */

public class LoadMoreLayout extends BaseLoadMoreLayout {

    public LoadMoreLayout(@NonNull Context context) {
        super(context);
    }

    public LoadMoreLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LoadMoreLayout(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
