package cn.xiaoxige.a2017_5_27demo;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;

/**
 * Created by 小稀革 on 2017/5/28.
 */

public class TestItemDecoration extends ItemDecoration {

    private Paint paint;

    public TestItemDecoration() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        for (int i = 0; i < parent.getChildCount(); i++) {
            if (i == 0) {
                continue;
            }

            View childAt = parent.getChildAt(i);
            int top = childAt.getTop() - 4;

            int left = childAt.getPaddingLeft();

            int bottom = childAt.getTop();

            int right = parent.getWidth() - childAt.getPaddingRight();


            c.drawRect(left, top, right, bottom, paint);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildAdapterPosition(view) != 0) {
            outRect.top = 4;
        }
    }
}
