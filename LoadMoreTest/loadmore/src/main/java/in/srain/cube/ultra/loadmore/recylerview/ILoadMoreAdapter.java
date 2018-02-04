package in.srain.cube.ultra.loadmore.recylerview;

import android.view.View;

/**
 * Created by ChaiHongwei on 2017/7/12 11:52.
 * 加载更多接口
 */

public interface ILoadMoreAdapter {
    View getFooterView();

    void addFooterView(View view);

    void removeFooterView(View view);
}
