package com.saintfine.customer.test.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.saintfine.customer.test.R;

/**
 * @author by zhuxiaoan on 2018/7/20 0020.
 */

public class OneActivity extends Activity {

    private Button btnSkipTwo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        btnSkipTwo = (Button) findViewById(R.id.btnSkipTwo);

        btnSkipTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OneActivity.this, TwoActivity.class));
            }
        });
    }
}
