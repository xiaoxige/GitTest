package cn.xiaoxige.annotationtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import cn.xiaoxige.anontation.ClassAnontation;
import cn.xiaoxige.anontation.MethodAnontation;

@ClassAnontation
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @MethodAnontation
    public void test() {

    }
}
