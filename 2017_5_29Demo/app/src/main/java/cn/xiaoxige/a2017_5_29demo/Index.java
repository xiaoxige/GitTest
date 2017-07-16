package cn.xiaoxige.a2017_5_29demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import cn.xiaoxige.a2017_5_29demo.fragment.ForeFragemt;
import cn.xiaoxige.a2017_5_29demo.fragment.OneFragemt;
import cn.xiaoxige.a2017_5_29demo.fragment.ThreeFragemt;
import cn.xiaoxige.a2017_5_29demo.fragment.TwoFragemt;

public class Index extends FragmentActivity {
    private TextView tvCenter;
    private FrameLayout frameLayout;
    private RadioGroup rgGroup;
    private Map<Integer, BaseFragment> fragmentMap;
    private int preFragmentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        tvCenter = (TextView) findViewById(R.id.tvCenter);
        frameLayout = (FrameLayout) findViewById(R.id.frameLayout);
        rgGroup = (RadioGroup) findViewById(R.id.rgGroup);

        initFragments();

        initListener();
    }

    private void initFragments() {
        fragmentMap = new HashMap<>();
        fragmentMap.put(R.id.one, new OneFragemt());
        fragmentMap.put(R.id.two, new TwoFragemt());
        fragmentMap.put(R.id.three, new ThreeFragemt());
        fragmentMap.put(R.id.fore, new ForeFragemt());
        preFragmentIndex = R.id.one;
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.frameLayout, fragmentMap.get(preFragmentIndex))
                .commit();
    }

    private void initListener() {
        tvCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Index.this, "Center", Toast.LENGTH_SHORT).show();
            }
        });

        rgGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                FragmentTransaction fragmentTransaction =
                        getSupportFragmentManager().beginTransaction();
                fragmentTransaction.hide(fragmentMap.get(preFragmentIndex));
                BaseFragment selectFragment = fragmentMap.get(checkedId);
                if (selectFragment.isAdded()) {
                    fragmentTransaction.show(selectFragment);
                } else {
                    fragmentTransaction.add(R.id.frameLayout, selectFragment);
                }
                fragmentTransaction.commit();
                preFragmentIndex = checkedId;
            }
        });
    }

    public static void showActivity(Context context) {
        context.startActivity(new Intent(context, Index.class));
    }
}
