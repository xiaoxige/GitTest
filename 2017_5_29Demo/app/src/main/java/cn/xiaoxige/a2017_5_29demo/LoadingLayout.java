package cn.xiaoxige.a2017_5_29demo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;

/**
 * Created by 小稀革 on 2017/5/30.
 */

public class LoadingLayout extends BaseLoadingLayout {

    public LoadingLayout(@NonNull Context context, @NonNull View view) {
        super(context, view);
    }

    @Override
    protected View getLoadingView() {
        return View.inflate(mContext, R.layout.error_layout, null);
    }

    @Override
    protected View getErrorView() {
        return LayoutInflater.from(mContext).inflate(R.layout.error_layout, mRootView, false);
    }

    @Override
    protected View getEmptyView() {
        return View.inflate(mContext, R.layout.error_layout, null);
    }

    @Override
    protected View getNotNetView() {
        return View.inflate(mContext, R.layout.error_layout, null);
    }
}
