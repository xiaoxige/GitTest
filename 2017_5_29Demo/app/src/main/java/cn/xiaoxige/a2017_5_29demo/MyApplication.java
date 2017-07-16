package cn.xiaoxige.a2017_5_29demo;

import android.app.Application;

import com.bugtags.library.Bugtags;

/**
 * Created by 小稀革 on 2017/5/29.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Bugtags.start("601b1a726c89103d4aedcb4b0a6849b9", this, Bugtags.BTGInvocationEventBubble);
    }

}
