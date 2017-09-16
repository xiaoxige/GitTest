package cn.xiaoxige.refreshtest;

import android.content.Context;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

/**
 * Created by 小稀革 on 2017/9/17.
 */

public class TopLoadingView extends FrameLayout {


    public TopLoadingView(@NonNull Context context) {
        this(context, null);
    }

    public TopLoadingView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopLoadingView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        View.inflate(context, R.layout.loading_view, this);
    }

    public void onPositionChange(float i){
        Log.e("TAG", "onPositionChange" + i);
    }


    public void onReleaseRefresh(){

        Log.e("TAG", "onReleaseRefresh");

    }

    public void onRefresh(){
        Log.e("TAG", "onRefresh");
    }

}
