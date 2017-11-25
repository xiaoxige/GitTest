package cn.xiaoxige.annotationtest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import cn.xiaoxige.anontation.ClassAnontation;
import cn.xiaoxige.anontation.Method2Anontation;
import cn.xiaoxige.anontation.MethodAnontation;

/**
 * Created by 小稀革 on 2017/11/26.
 */
@ClassAnontation
public class TwoActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Method2Anontation
    @MethodAnontation
    public void test(String name, int age) {
    }
}
