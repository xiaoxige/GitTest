package cn.xiaoxige.a2017_5_29demo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by 小稀革 on 2017/5/29.
 */

public class ViewpagerAdapter extends PagerAdapter {

    private Context mContext;
    private List<ImageView> mImageViews;

    public ViewpagerAdapter(Context context, List<ImageView> imageViews) {
        mContext = context;
        mImageViews = imageViews;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView = mImageViews.get(position);
        container.addView(imageView);
        return imageView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return mImageViews == null ? 0 : mImageViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

}
