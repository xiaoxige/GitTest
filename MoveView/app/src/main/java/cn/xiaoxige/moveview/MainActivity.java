package cn.xiaoxige.moveview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextSwitcher;
import android.widget.Toast;

import cn.xiaoxige.moveview.view.BaseEditView;

public class MainActivity extends AppCompatActivity {

    private EditView viewFirst;
    private EditView viewSecond;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewFirst = (EditView) findViewById(R.id.viewFirst);
        viewSecond = (EditView) findViewById(R.id.viewSecond);

        viewFirst.setIsEditState(true);
        viewSecond.setIsEditState(false);

        viewFirst.setMsg("剪发读卡机首付款拉的屎解放路飞机离开房间 防静电卡 发电量卡视角啦就发发大水口了附近的萨克雷锋");
        viewSecond.setMsg("剪发读卡机首付款拉的屎解放路飞机离开房间 防静电卡 发电量卡视角啦就发发大水口了附近的萨克雷锋");

        viewFirst.setCallback(new BaseEditView.OptCallback() {
            @Override
            public void editStateChanged(View v, boolean isEdit) {
                Toast.makeText(MainActivity.this, "first: state=  " + isEdit, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void opt(View v, int opt) {
                Toast.makeText(MainActivity.this, "first: clickd", Toast.LENGTH_SHORT).show();
            }
        });

        viewSecond.setCallback(new BaseEditView.OptCallback() {
            @Override
            public void editStateChanged(View v, boolean isEdit) {
                Toast.makeText(MainActivity.this, "second: state=  " + isEdit, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void opt(View v, int opt) {
                Toast.makeText(MainActivity.this, "second: clickd", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
