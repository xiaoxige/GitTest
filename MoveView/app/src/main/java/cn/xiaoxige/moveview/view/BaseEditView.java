package cn.xiaoxige.moveview.view;

import android.content.Context;
import android.support.annotation.CallSuper;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;

/**
 * Created by xiaoxige on 2018/3/6.
 * 可以编辑的顶层View
 */

public abstract class BaseEditView extends ViewGroup {

    protected Context mContext;

    private CheckBox cbEdit;
    private FrameLayout flContainer;

    private int mNeedMoveWidth;
    private int mCbEditWidth;
    private int mCbEditHeight;
    private int mContainerWidht;
    private int mContainerHeight;

    private boolean mIsEdit;

    protected OptCallback mCallback;

    public BaseEditView(Context context) {
        this(context, null);
    }

    public BaseEditView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BaseEditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mContext = context;
        initView();
        initData();
        registerListener();
    }

    @CallSuper
    protected void initView() {

        cbEdit = createLeftCb();
        flContainer = createContaier();

        addView(cbEdit);
        addView(flContainer);
        // 添加自定义的详细布局
        addDetailView(flContainer);
    }

    @CallSuper
    protected void initData() {

    }

    @CallSuper
    protected void registerListener() {
        cbEdit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (mCallback != null) {
                    mCallback.editStateChanged(compoundButton, b);
                }
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);

        if (widthMode != MeasureSpec.EXACTLY) {
            throw new RuntimeException("宽度不明确!");
        }

        measureChild(cbEdit, widthMeasureSpec, heightMeasureSpec);
        measureChild(flContainer, widthMeasureSpec, heightMeasureSpec);

        mCbEditWidth = mNeedMoveWidth = cbEdit.getMeasuredWidth();
        mCbEditHeight = cbEdit.getMeasuredHeight();
        mContainerWidht = widthSize;
        mContainerHeight = flContainer.getMeasuredHeight();

        int heightSize = mCbEditHeight > mContainerHeight ? mCbEditHeight : mContainerHeight;

        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {
        int start = mIsEdit ? 0 : -mNeedMoveWidth;
        cbEdit.layout(start, 0, start + mCbEditWidth, mCbEditHeight);
        flContainer.layout(start + mCbEditWidth, 0, start + mCbEditWidth + mContainerWidht, mContainerHeight);
    }

    public void setIsEditState(boolean isEdit) {
        mIsEdit = isEdit;
        requestLayout();
    }

    protected void changedDetailView(View detailView) {
        flContainer.removeAllViews();
        if (detailView == null) {
            return;
        }
        flContainer.addView(detailView);
        invalidate();
    }

    private CheckBox createLeftCb() {
        CheckBox checkBox = new CheckBox(mContext);
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        checkBox.setLayoutParams(params);
        // todo 设置其他属性
        return checkBox;
    }

    private FrameLayout createContaier() {
        FrameLayout frameLayout = new FrameLayout(mContext);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        // todo 设置其他属性
        frameLayout.setLayoutParams(params);
        return frameLayout;
    }

    protected abstract void addDetailView(ViewGroup container);

    public void setCallback(OptCallback callback) {
        this.mCallback = callback;
    }

    public interface OptCallback {
        void editStateChanged(View v, boolean isEdit);

        void opt(View v, int opt);
    }

}
