package cn.xiaoxige.a2017_5_29demo;

import android.app.Activity;
import android.view.MotionEvent;

import com.bugtags.library.Bugtags;

/**
 * Created by 小稀革 on 2017/5/29.
 */

public class BaseActivity extends Activity {

    @Override
    protected void onResume() {
        super.onResume();

        Bugtags.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Bugtags.onPause(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Bugtags.onDispatchTouchEvent(this, ev);
        return super.dispatchTouchEvent(ev);
    }
}
