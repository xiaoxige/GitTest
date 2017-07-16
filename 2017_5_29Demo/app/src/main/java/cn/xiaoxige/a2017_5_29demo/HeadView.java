package cn.xiaoxige.a2017_5_29demo;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.lcodecore.tkrefreshlayout.IHeaderView;
import com.lcodecore.tkrefreshlayout.OnAnimEndListener;

/**
 * Created by 小稀革 on 2017/5/30.
 */

public class HeadView implements IHeaderView {
    private Context mContext;

    public HeadView(Context context) {
        mContext = context;
    }

    @Override
    public View getView() {
        return View.inflate(mContext, R.layout.headview, null);
    }

    @Override
    public void onPullingDown(float fraction, float maxHeadHeight, float headHeight) {
        Log.e("TAG", "下啦中" + fraction);
    }

    @Override
    public void onPullReleasing(float fraction, float maxHeadHeight, float headHeight) {
        Log.e("TAG", "释放中" + fraction);
    }

    @Override
    public void startAnim(float maxHeadHeight, float headHeight) {
        Log.e("TAG", "startAnim");
    }

    @Override
    public void onFinish(OnAnimEndListener animEndListener) {
        Log.e("TAG", "完成");
        animEndListener.onAnimEnd();
    }

    @Override
    public void reset() {

        Log.e("TAG", "重设");

    }
}
