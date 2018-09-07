package com.saintfine.customer.test.xiaoxigetest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.saintfine.customer.test.R;

import java.util.ArrayList;
import java.util.List;

public class XiaoxigeActivity extends Activity {

    private XiaoxigeTwoMenuView viewXiaoxigeMenuView1;
    private XiaoxigeTwoMenuView viewXiaoxigeMenuView2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiaoxige);

        viewXiaoxigeMenuView1 = (XiaoxigeTwoMenuView) findViewById(R.id.viewXiaoxigeMenuView1);
        viewXiaoxigeMenuView2 = (XiaoxigeTwoMenuView) findViewById(R.id.viewXiaoxigeMenuView2);

        List<XiaoxigeTwoMenuView.MenuInfo> menuInfo1 = new ArrayList<>();
        List<XiaoxigeTwoMenuView.MenuInfo> menuInfo2 = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            menuInfo1.add(new XiaoxigeTwoMenuView.MenuInfo());
            menuInfo2.add(new XiaoxigeTwoMenuView.MenuInfo());
        }
        viewXiaoxigeMenuView1.addMenu(menuInfo1, false);
        viewXiaoxigeMenuView2.addMenu(menuInfo2, false);
    }
}
