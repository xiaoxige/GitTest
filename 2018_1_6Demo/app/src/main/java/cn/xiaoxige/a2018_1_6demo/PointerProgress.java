package cn.xiaoxige.a2018_1_6demo;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

/**
 * Created by zhuxiaoan on 2018/1/6.
 */

public class PointerProgress extends LinearLayout {

    private Context mContext;
    private ProgressBar pbProgressBar;
    private TextView tvProgress;

    private int progressPointerWidth;
    private int progressWidth;
    private int viewWidth;

    private int maxProgress;

    private int mProgress;

    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int progress = msg.arg1;
            if (progress != mProgress) {
                if (progress > mProgress) {
                    mProgress++;
                } else if (progress < mProgress) {
                    mProgress--;
                }
                Message message = Message.obtain();
                message.arg1 = progress;
                sendMessageDelayed(message, 20);
            }
            updateProgress();
        }
    };

    public PointerProgress(Context context) {
        this(context, null);
    }

    public PointerProgress(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PointerProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        setOrientation(LinearLayout.VERTICAL);
        initView();
        initData();
    }

    private void initView() {
        View.inflate(mContext, R.layout.view_pointer_progress, this);
        pbProgressBar = (ProgressBar) findViewById(R.id.pbProgressBar);
        tvProgress = (TextView) findViewById(R.id.tvProgress);
    }

    private void initData() {
        maxProgress = 100;
        mProgress = 0;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth();
        progressPointerWidth = tvProgress.getMeasuredWidth();
        progressWidth = pbProgressBar.getMeasuredWidth();
    }

    public void setMax(int maxProgress) {
        this.maxProgress = maxProgress;
        invalidate();
    }

    public void setProgress(int progress, boolean animate) {
        progress = Math.min(maxProgress, Math.max(0, progress));
        handler.removeCallbacksAndMessages(null);
        if (animate) {
            Message message = Message.obtain();
            message.arg1 = progress;
            handler.sendMessage(message);
        } else {
            mProgress = progress;
            updateProgress();
        }
    }

    public int getProgress() {
        return pbProgressBar.getProgress();
    }

    private void updateProgress() {
        mProgress = Math.min(maxProgress, Math.max(0, mProgress));
        pbProgressBar.setProgress(mProgress);
        tvProgress.setText(mProgress + "%");
        // mProgress / 100 = x / progressWidth
        int positionX = mProgress * progressWidth / maxProgress;
        positionX = Math.min(progressWidth - progressPointerWidth, Math.max(0, positionX));
        tvProgress.setTranslationX(positionX);
        requestLayout();
    }
}
