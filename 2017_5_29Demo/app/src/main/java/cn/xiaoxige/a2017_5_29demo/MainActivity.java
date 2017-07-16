package cn.xiaoxige.a2017_5_29demo;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    private ViewPager viewPager;
    private ViewpagerAdapter adapter;
    private List<ImageView> imageViews;
    private LinearLayout llPoint;
    private int preSelectPontIndex;
    private Button btnEnterIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        llPoint = (LinearLayout) findViewById(R.id.llPoint);
        btnEnterIndex = (Button) findViewById(R.id.btnEnterIndex);
        imageViews = new ArrayList<>();

        ImageView imageView = new ImageView(MainActivity.this);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(R.mipmap.guide_1);
        imageViews.add(imageView);
        imageView = new ImageView(MainActivity.this);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(R.mipmap.guide_2);
        imageViews.add(imageView);
        imageView = new ImageView(MainActivity.this);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setImageResource(R.mipmap.guide_3);
        imageViews.add(imageView);
        adapter = new ViewpagerAdapter(MainActivity.this, imageViews);
        viewPager.setAdapter(adapter);

        initPoints();

        initlistener();
    }

    /**
     * +...
     */
    private void initPoints() {
        TextView pointView;
        preSelectPontIndex = 0;
        for (int i = 0; i < 3; i++) {
            pointView = new TextView(MainActivity.this);
            pointView.setBackgroundResource(R.drawable.selector_point);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(12, 12);
            pointView.setEnabled(false);
            params.leftMargin = i == 0 ? 0 : 6;
            pointView.setLayoutParams(params);
            llPoint.addView(pointView);
        }
        llPoint.getChildAt(preSelectPontIndex).setEnabled(true);
    }

    private void initlistener() {

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                llPoint.getChildAt(preSelectPontIndex).setEnabled(false);
                preSelectPontIndex = position;
                llPoint.getChildAt(preSelectPontIndex).setEnabled(true);
                btnEnterIndex.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void btnEnterIndex(View v) {
        Index.showActivity(MainActivity.this);
    }
}
