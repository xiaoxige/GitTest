package com.saintfine.customer.test.xiaoxigetest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingChild;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.saintfine.customer.test.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by zhuxiaoan on 2018/9/6 0006.
 */

public class XiaoxigeTwoMenuView extends ViewGroup implements NestedScrollingChild {

    private static final int PORTRAIT_SPACING = 40;

    private Context mContext;

    private int mCanMoveY;
    private int mMoveY;

    private List<MenuItem> mMenuItems;

    public XiaoxigeTwoMenuView(Context context) {
        this(context, null);
    }

    public XiaoxigeTwoMenuView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XiaoxigeTwoMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        mMenuItems = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int size = mMenuItems.size();
        if (size == 0) {
            setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
            return;
        }

        int width = MeasureSpec.getSize(widthMeasureSpec);

        MenuItem menuItem;
        for (int i = 0; i < size; i++) {
            menuItem = mMenuItems.get(i);
            if (menuItem.isMeasure) {
                continue;
            }
            View view = menuItem.getView();
            measureChild(view, 0, 0);
            menuItem.setViewWidth(view.getMeasuredWidth());
            menuItem.setViewHeight(view.getMeasuredHeight());
        }

        // 以第一个为例（每一个菜单是大小一样的，至少现在只考虑相同）
        menuItem = mMenuItems.get(0);
        View itemView = menuItem.getView();
        int itemWidth = menuItem.getViewWidth();
        int itemHeight = menuItem.getViewHeight();

        // 伸缩后的横向间距计算
        int foldTransverseSpacing = (width - itemWidth * 6) / (6 + 1);
        // 展开后的横向间距计算
        int nonfoldTransverseSpacing = (width - itemWidth * 3) / (3 + 1);

        // 设置伸缩和非伸缩位置数据
        for (int i = 0; i < size; i++) {
            menuItem = mMenuItems.get(i);
            if (menuItem.isMeasure) {
                continue;
            }
            // 设置伸缩后的位置数据
            @SuppressLint("DrawAllocation")
            Point foldPoint = new Point();
            foldPoint.set((i + 1) * foldTransverseSpacing + i * itemWidth, getPaddingTop());
            menuItem.setFoldPoint(foldPoint);
            // 设置非伸缩后的位置数据
            @SuppressLint("DrawAllocation")
            Point nonFoldPoint = new Point();
            nonFoldPoint.set((i % 3 + 1) * nonfoldTransverseSpacing + (i % 3) * itemWidth, i < 3 ? getPaddingTop() : getPaddingTop() + itemHeight + PORTRAIT_SPACING);
            menuItem.setNonFoldPoint(nonFoldPoint);

            if (mMoveY == 0) {
                menuItem.currentX = nonFoldPoint.x;
                menuItem.currentY = nonFoldPoint.y;
            }

            menuItem.setCanGlidingX(Math.abs(nonFoldPoint.x - foldPoint.x));
            menuItem.setCanGlidingY(Math.abs(nonFoldPoint.y - foldPoint.y));

            menuItem.isMeasure = true;
        }

        View textDescView = ((ViewGroup) itemView).getChildAt(1);
        int measuredHeight = textDescView.getMeasuredHeight();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) textDescView.getLayoutParams();
        mCanMoveY = measuredHeight + PORTRAIT_SPACING + itemHeight + layoutParams.topMargin;
        setMeasuredDimension(widthMeasureSpec, itemHeight * 2 + getPaddingTop() + getPaddingBottom() + PORTRAIT_SPACING - mMoveY);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int size = mMenuItems.size();
        if (size == 0) {
            return;
        }
        MenuItem menuItem;
        for (int i = 0; i < size; i++) {
            menuItem = mMenuItems.get(i);
            View view = menuItem.getView();
            int currentX = menuItem.currentX;
            int currentY = menuItem.currentY;

            view.layout(currentX, currentY, currentX + menuItem.getViewWidth(), currentY + menuItem.getViewHeight());

//            view.layout(foldPoint.x, foldPoint.y, foldPoint.x + menuItem.getViewWidth(), foldPoint.y + menuItem.getViewHeight());
        }
    }

    /**
     * 改方法会有好多重复的代码， 不在合并了， 感觉这样好理解点
     *
     * @param dx
     * @param dy
     * @param consumed
     * @param offsetInWindow
     * @return
     */
    @SuppressWarnings("ConstantConditions")
    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, @Nullable int[] consumed, @Nullable int[] offsetInWindow) {
        int beforeMoveY = mMoveY;
        if (Math.abs(dy) > 0 && isTelescopic()) {
            if (dy > 0) {
                // 上 +
                // 改变父框大小
                int tempResult = mMoveY + dy;
                mMoveY = Math.min(tempResult, mCanMoveY);
                consumed[1] = mMoveY - beforeMoveY;
                if (consumed[1] != 0) {
                    // 改变子框位置（向foldPoint位置进攻）
                    int size = mMenuItems.size();
                    MenuItem menuItem;
                    for (int i = 0; i < size; i++) {
                        menuItem = mMenuItems.get(i);
                        float progress = ((float) mMoveY) / mCanMoveY;
                        // 应该现在移动位置距离
                        float x = menuItem.getCanGlidingX() * progress;
                        float y = menuItem.getCanGlidingY() * progress;
                        // 判断当前x位置是否在最终进攻x位置的方向
                        if (menuItem.currentX > menuItem.foldPoint.x) {
                            menuItem.currentX = (int) (menuItem.nonFoldPoint.x - x);
                        } else {
                            menuItem.currentX = (int) (menuItem.nonFoldPoint.x + x);
                        }
                        // 判断当前y位置是否在最终进攻y位置的方向
                        if (menuItem.currentY > menuItem.foldPoint.y) {
                            menuItem.currentY = (int) (menuItem.nonFoldPoint.y - y);
                        } else {
                            menuItem.currentY = (int) (menuItem.nonFoldPoint.y + y);
                        }
                    }
                }
            } else {
                // 下 -
                // 改变父框大小（向nonFoldPoint位置进攻）
                dy = Math.abs(dy);
                int tempResult = mMoveY - dy;
                mMoveY = Math.max(tempResult, 0);
                consumed[1] = mMoveY - beforeMoveY;
                if (consumed[1] != 0) {
                    // 改变子框位置
                    int size = mMenuItems.size();
                    MenuItem menuItem;
                    for (int i = 0; i < size; i++) {
                        menuItem = mMenuItems.get(i);
                        float progress = ((float) mMoveY) / mCanMoveY;
                        // 应该现在移动位置距离
                        float x = menuItem.getCanGlidingX() * progress;
                        float y = menuItem.getCanGlidingY() * progress;
                        // 判断当前x位置是否在最终进攻x位置的方向
                        if (menuItem.currentX > menuItem.foldPoint.x) {
                            menuItem.currentX = (int) (menuItem.nonFoldPoint.x - x);
                        } else {
                            menuItem.currentX = (int) (menuItem.nonFoldPoint.x + x);
                        }
                        // 判断当前y位置是否在最终进攻y位置的方向
                        if (menuItem.currentY > menuItem.foldPoint.y) {
                            menuItem.currentY = (int) (menuItem.nonFoldPoint.y - y);
                        } else {
                            menuItem.currentY = (int) (menuItem.nonFoldPoint.y + y);
                        }
                    }
                }
            }
        }

        if (beforeMoveY != mMoveY) {
            requestLayout();
        }

        return super.dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    public void addMenu(List<MenuInfo> menuInfos, boolean isAppend) {
        if (!isAppend) {
            mMenuItems.clear();
        }
        removeAllViews();
        if (menuInfos != null && !menuInfos.isEmpty()) {
            int size = menuInfos.size();
            if (size != 6) {
                throw new RuntimeException("暂只支持6个菜单");
            }
            // 添加menu
            generateMenu(menuInfos);
        }
        requestLayout();
    }

    private void generateMenu(List<MenuInfo> menuInfos) {
        MenuItem menuItem;
        for (MenuInfo menuInfo : menuInfos) {
            View view = createMenuView(menuInfo);
            if (view == null) {
                continue;
            }
            bindRegister(view, menuInfo);
            addView(view);

            menuItem = new MenuItem();
            menuItem.setMenuInfo(menuInfo);
            menuItem.setView(view);

            mMenuItems.add(menuItem);
        }
    }

    private void bindRegister(View view, MenuInfo menuInfo) {

    }

    private View createMenuView(MenuInfo menuItem) {
        View view = View.inflate(mContext, R.layout.item_menu, null);
        // TODO: 2018/9/6 0006 绑定数据
        return view;
    }

    public boolean isTelescopic() {
        if (mMenuItems.size() > 0) {
            return mMoveY >= 0 && mMoveY <= mCanMoveY;
        }
        return false;
    }

    private class MenuItem {
        private MenuInfo menuInfo;
        private View view;
        private boolean isMeasure;

        private int viewHeight;
        private int viewWidth;
        // 未折叠后的位置信息
        private Point nonFoldPoint;
        // 折叠后的位置信息
        private Point foldPoint;

        // 可滑动x的长度(|未折叠的x - 折叠的x|)
        private int canGlidingX;
        // 可滑动y的长度(|未折叠的y - 折叠的y|)
        private int canGlidingY;

        // 当前x位置
        private int currentX;
        // 当前y位置
        private int currentY;

        public MenuInfo getMenuInfo() {
            return menuInfo;
        }

        public void setMenuInfo(MenuInfo menuInfo) {
            this.menuInfo = menuInfo;
        }

        public View getView() {
            return view;
        }

        public void setView(View view) {
            this.view = view;
        }

        public int getViewHeight() {
            return viewHeight;
        }

        public void setViewHeight(int viewHeight) {
            this.viewHeight = viewHeight;
        }

        public int getViewWidth() {
            return viewWidth;
        }

        public void setViewWidth(int viewWidth) {
            this.viewWidth = viewWidth;
        }

        public Point getNonFoldPoint() {
            return nonFoldPoint;
        }

        public void setNonFoldPoint(Point nonFoldPoint) {
            this.nonFoldPoint = nonFoldPoint;
        }

        public Point getFoldPoint() {
            return foldPoint;
        }

        public void setFoldPoint(Point foldPoint) {
            this.foldPoint = foldPoint;
        }

        public int getCanGlidingX() {
            return canGlidingX;
        }

        public void setCanGlidingX(int canGlidingX) {
            this.canGlidingX = canGlidingX;
        }

        public int getCanGlidingY() {
            return canGlidingY;
        }

        public void setCanGlidingY(int canGlidingY) {
            this.canGlidingY = canGlidingY;
        }

        public int getCurrentX() {
            return currentX;
        }

        public void setCurrentX(int currentX) {
            this.currentX = currentX;
        }

        public int getCurrentY() {
            return currentY;
        }

        public void setCurrentY(int currentY) {
            this.currentY = currentY;
        }

        public boolean isMeasure() {
            return isMeasure;
        }

        public void setMeasure(boolean measure) {
            isMeasure = measure;
        }
    }

    static class MenuInfo {
        private int sign;
        private int img;
        private String desc;

        public MenuInfo() {
        }

        public MenuInfo(int sign, int img, String desc) {
            this.sign = sign;
            this.img = img;
            this.desc = desc;
        }

        public int getSign() {
            return sign;
        }

        public void setSign(int sign) {
            this.sign = sign;
        }

        public int getImg() {
            return img;
        }

        public void setImg(int img) {
            this.img = img;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}