package cn.xiaoxige.customview;

import android.graphics.Bitmap;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;
import com.facebook.stetho.Stetho;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by 小稀革 on 2017/11/3.
 */

public class MyApplication extends MultiDexApplication {

    @Override
    public void onCreate() {
        super.onCreate();

        Log.e("TAG", "MyApplication");

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }

        Set<RequestListener> listeners = new HashSet<>();
        listeners.add(new RequestLoggingListener());
        ImagePipelineConfig imageConfig = ImagePipelineConfig.newBuilder(this)
                .setBitmapsConfig(Bitmap.Config.RGB_565)
                .setRequestListeners(listeners)
                .setDownsampleEnabled(true)
                .build();
        Fresco.initialize(this, imageConfig);

    }

}

