package cn.xiaoxige.a2017_5_27demo;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.luck.picture.lib.model.FunctionConfig;
import com.luck.picture.lib.model.FunctionOptions;
import com.luck.picture.lib.model.PictureConfig;

/**
 * Created by 小稀革 on 2017/5/29.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        Stetho.initializeWithDefaults(this);

        FunctionOptions options = new FunctionOptions.Builder()
                .setType(FunctionConfig.TYPE_IMAGE)
                .setEnablePreview(true)
                .setGif(false)
                .setNumComplete(true)
                .setShowCamera(true)
                .setEnableCrop(false)
                .setMaxSelectNum(50)
                .setCompress(true)
                .setSelectMode(FunctionConfig.MODE_SINGLE)
                .create();
        PictureConfig.getInstance().init(options);
    }
}
