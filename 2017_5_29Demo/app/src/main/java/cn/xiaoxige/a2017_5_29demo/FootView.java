package cn.xiaoxige.a2017_5_29demo;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.lcodecore.tkrefreshlayout.IBottomView;

/**
 * Created by 小稀革 on 2017/5/30.
 */

public class FootView implements IBottomView {
    private Context mContext;

    public FootView (Context context){
        mContext = context;
    }
    @Override
    public View getView() {
        return View.inflate(mContext, R.layout.footview, null);
    }

    @Override
    public void onPullingUp(float fraction, float maxBottomHeight, float bottomHeight) {
        Log.e("TAG", "加载中" + fraction);
    }

    @Override
    public void startAnim(float maxBottomHeight, float bottomHeight) {
        Log.e("TAG", "开始");
    }


    @Override
    public void onPullReleasing(float fraction, float maxBottomHeight, float bottomHeight) {
        Log.e("TAG", "释放中" + fraction);
    }

    @Override
    public void onFinish() {
        Log.e("TAG", "完成");
    }

    @Override
    public void reset() {
        Log.e("TAG", "重设");
    }
}
