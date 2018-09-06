package com.saintfine.customer.test.xiaoxigetest;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.saintfine.customer.test.R;

import java.util.ArrayList;
import java.util.List;

public class XiaoxigeActivity extends Activity {

    private XiaoxigeTwoMenuView viewXiaoxigeMenuView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiaoxige);

        viewXiaoxigeMenuView = (XiaoxigeTwoMenuView) findViewById(R.id.viewXiaoxigeMenuView);

        List<XiaoxigeTwoMenuView.MenuInfo> menuInfo = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            menuInfo.add(new XiaoxigeTwoMenuView.MenuInfo());
        }
        viewXiaoxigeMenuView.addMenu(menuInfo, false);
    }
}
