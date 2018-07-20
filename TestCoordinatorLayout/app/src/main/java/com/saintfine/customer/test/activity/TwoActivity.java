package com.saintfine.customer.test.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.saintfine.customer.test.R;

import junit.framework.Assert;

/**
 * @author by zhuxiaoan on 2018/7/20 0020.
 */

public class TwoActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
    }
}
