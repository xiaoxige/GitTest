package cn.xiaoxige.updategradle;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by xiaoxige on 2018/3/11.
 */

public class MyLayoutManager extends RecyclerView.LayoutManager {

    private Context mContext;

    private int mTotleHeight;
    private int mVerticlScrollOffset;

    private SparseArray<Rect> mAllItemFrames;
    private SparseBooleanArray mHasAttachedItems;

    public MyLayoutManager(Context context) {
        this.mContext = context;
        this.mVerticlScrollOffset = 0;
        mAllItemFrames = new SparseArray<>();
        mHasAttachedItems = new SparseBooleanArray();
    }

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        this.mTotleHeight = 0;
        detachAndScrapAttachedViews(recycler);
        int offsetY = 0;
        int itemCount = getItemCount();
        for (int i = 0; i < itemCount; i++) {
            View view = recycler.getViewForPosition(i);
            addView(view);
            measureChildWithMargins(view, 0, 0);
            int width = getDecoratedMeasuredWidth(view);
            int height = getDecoratedMeasuredHeight(view);
//            layoutDecorated(view, 0, offsetY, width, offsetY + height);

            Rect rect = mAllItemFrames.get(i);
            if (rect == null) {
                rect = new Rect();
            }
            rect.set(0, offsetY, width, offsetY + height);
            mAllItemFrames.put(i, rect);
            mHasAttachedItems.put(i, false);

            offsetY += height;
            this.mTotleHeight += height;
        }
        mTotleHeight = Math.max(mTotleHeight, getVerticalSpace());
        recyclerAndFillItems(recycler, state);
    }

    private void recyclerAndFillItems(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (state.isPreLayout()) {
            return;
        }
        Rect displayRect = new Rect(0, mVerticlScrollOffset,
                getHorizontalSpace(), mVerticlScrollOffset + getVerticalSpace());
        Rect childRect = new Rect();
        int itemCount = getItemCount();
        for (int i = 0; i < itemCount; i++) {
            View child = getChildAt(i);
            if (child == null) {
                continue;
            }
            childRect.left = getDecoratedLeft(child);
            childRect.top = getDecoratedTop(child);
            childRect.right = getDecoratedRight(child);
            childRect.bottom = getDecoratedBottom(child);
            if (!Rect.intersects(displayRect, childRect)) {
                removeAndRecycleView(child, recycler);
            }
        }

        for (int i = 0; i < itemCount; i++) {
            if (Rect.intersects(displayRect, mAllItemFrames.get(i))) {
                View scrap = recycler.getViewForPosition(i);
                measureChildWithMargins(scrap, 0, 0);
                addView(scrap);
                Rect rect = mAllItemFrames.get(i);
                layoutDecorated(scrap, rect.left,
                        rect.top - mVerticlScrollOffset,
                        rect.right,
                        rect.bottom - mVerticlScrollOffset);
            }
        }

    }

    @Override
    public boolean canScrollVertically() {
        return true;
    }

    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
        int travel = dy;

        if (mVerticlScrollOffset + dy < 0) {
            travel = -mVerticlScrollOffset;
        } else if (mVerticlScrollOffset + dy > mTotleHeight - getVerticalSpace()) {
            travel = mTotleHeight - getVerticalSpace() - mVerticlScrollOffset;
        }
        mVerticlScrollOffset += travel;
        offsetChildrenVertical(-travel);

        recyclerAndFillItems(recycler, state);

        Log.e("TAG", "" + getChildCount());

        return travel;
    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }

    private int getHorizontalSpace() {
        return getWidth() - getPaddingLeft() - getPaddingRight();
    }

}
