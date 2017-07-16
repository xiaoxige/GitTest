package cn.xiaoxige.a2017_5_29demo;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 小稀革 on 2017/5/30.
 */

public abstract class BaseLoadingLayout {

    public static final int LOADINGPAGER = 0;
    public static final int ERRORPAGER = 1;
    public static final int EMPTYPAGER = 2;
    public static final int NOTNETPAGER = 3;
    public static final int CENTERPAGER = 4;

    protected ViewGroup mRootView;
    private View mCotentView;
    private Map<Integer, View> mViewMaps;
    protected Context mContext;

    private Button mBtnError;
    private Button mBtnEmpty;
    private Button mBtnNotNet;

    public BaseLoadingLayout(@NonNull Context context, @NonNull View view) {
        mContext = context;
        mCotentView = view;
        mRootView = (ViewGroup) view.getParent();
        mViewMaps = new HashMap<>();
        mViewMaps.put(CENTERPAGER, mCotentView);
    }


    public void isShowBtnError(boolean b) {
        if (mBtnError != null) {
            mBtnError.setVisibility(b ? View.VISIBLE : View.GONE);
        }
    }

    public void isShowBtnEmpty(boolean b) {
        if (mBtnEmpty != null) {
            mBtnEmpty.setVisibility(b ? View.VISIBLE : View.GONE);
        }
    }

    public void isShowBtnNotNet(boolean b) {
        if (mBtnNotNet != null) {
            mBtnNotNet.setVisibility(b ? View.VISIBLE : View.GONE);
        }
    }

    public void change(@LoadingStatus int status) {
        changeStatus(status);
    }

    private void changeStatus(@LoadingStatus int status) {
        String viewName = null;
        View view = mViewMaps.get(status);
        if (view == null) {
            switch (status) {
                case BaseLoadingLayout.LOADINGPAGER:
                    // 加载界面
                    view = getLoadingView();
                    viewName = "loadingView";
                    break;
                case BaseLoadingLayout.ERRORPAGER:
                    // 错误界面
                    view = getErrorView();
                    viewName = "errorView";
                    break;
                case BaseLoadingLayout.EMPTYPAGER:
                    // 空界面
                    view = getEmptyView();
                    viewName = "emptyView";
                    break;
                case BaseLoadingLayout.NOTNETPAGER:
                    // 没有网络界面
                    view = getNotNetView();
                    viewName = "notNetView";
                    break;
                case BaseLoadingLayout.CENTERPAGER:
                    // 内容界面
                    viewName = "cententView";
                    view = mCotentView;
                    break;
            }
            if (view == null) {
                throw new NullPointerException("Are you sure " + viewName + "not null?");
            }

            ViewGroup.LayoutParams layoutParams
                    = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            view.setLayoutParams(layoutParams);

            mViewMaps.put(status, view);
        }
        // 得到要显示的 view 视图
        mRootView.addView(view);
    }

    protected abstract View getLoadingView();

    protected abstract View getErrorView();

    protected abstract View getEmptyView();

    protected abstract View getNotNetView();

    private View findViewById(@NonNull View rootView, @IdRes int id) {
        return rootView.findViewById(id);
    }

    @Documented
    @Retention(RetentionPolicy.SOURCE)
    @IntDef({LOADINGPAGER, ERRORPAGER, EMPTYPAGER, NOTNETPAGER, CENTERPAGER})
    public @interface LoadingStatus {
    }
}
