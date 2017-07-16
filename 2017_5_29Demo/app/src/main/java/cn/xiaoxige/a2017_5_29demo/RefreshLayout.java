package cn.xiaoxige.a2017_5_29demo;

import android.content.Context;
import android.util.AttributeSet;

import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.BallPulseView;
import com.lcodecore.tkrefreshlayout.header.SinaRefreshView;

/**
 * Created by 小稀革 on 2017/5/30.
 */

public class RefreshLayout extends TwinklingRefreshLayout {
    public RefreshLayout(Context context) {
        this(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        setDefaultParams(context);
    }

    private void setDefaultParams(Context context) {
        setHeaderView(new SinaRefreshView(context));
        setBottomView(new BallPulseView(context));
    }
}
