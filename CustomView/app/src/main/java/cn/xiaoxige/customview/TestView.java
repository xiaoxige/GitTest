package cn.xiaoxige.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 小稀革 on 2017/10/28.
 */

public class TestView extends View {

    private Context mContext;
    private int mDefaultSize;
    private CircularArc mDefaultArc;
    private CircularArc mProgressArc;

    private int mProgress;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int progress = msg.arg1;
            if (progress < mProgress) {
                mProgress -= 1;
            } else if (progress > mProgress) {
                mProgress += 1;
            } else {
                return;
            }
            changeProgress(mProgress);
            Message message = Message.obtain();
            message.arg1 = progress++;
            sendMessageDelayed(message, 20);
        }
    };


    public TestView(Context context) {
        this(context, null);
    }

    public TestView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TestView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        initData();
    }

    private void initData() {

        mDefaultArc = new CircularArc();
        mProgressArc = new CircularArc();

        int defaultSize = dip2px(mContext, 40);
        int strokeWidth = dip2px(mContext, 8);

        mDefaultSize = defaultSize;
        mProgress = 0;

        Paint defaultPaint = mDefaultArc.getPaint();
        defaultPaint.setAntiAlias(true);
        defaultPaint.setColor(Color.BLACK);
        defaultPaint.setStrokeWidth(strokeWidth);
        defaultPaint.setStyle(Paint.Style.STROKE);

        Paint progressPaint = mProgressArc.getPaint();
        progressPaint.setAntiAlias(true);
        progressPaint.setColor(Color.RED);
        progressPaint.setStrokeWidth(strokeWidth - 4);
        progressPaint.setStyle(Paint.Style.STROKE);

    }

    int centerX;
    int centerY;
    int progressLineR;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int resultWidth = widthSize;
        int resultHeight = heightSize;

        RectF defaultRectF = mDefaultArc.getRectF();
        RectF progressRectF = mProgressArc.getRectF();

        /**
         * 不管长或者宽，只要有一个长度不确定，就是用默认宽度
         */
        if (widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY) {
        } else {
            if (widthMode == MeasureSpec.EXACTLY) {
                resultHeight = mDefaultSize;
            } else if (heightMode == MeasureSpec.EXACTLY) {
                resultWidth = mDefaultSize;
            } else {
                resultHeight = resultWidth = mDefaultSize;
            }
        }
        int centerWidth = resultWidth / 2;
        int centerHeight = resultHeight / 2;
        centerX = centerWidth;
        centerY = centerHeight;
        int size = Math.min(resultWidth, resultHeight) / 2;
        defaultRectF.set(centerWidth - size, centerHeight - size, centerWidth + size, centerHeight + size);
        progressRectF.set(centerWidth - size + 4, centerHeight - size + 4, centerWidth + size - 4, centerHeight + size - 4);
        progressLineR = (int) (size - mDefaultArc.getPaint().getStrokeWidth() * 3 / 2);
        setMeasuredDimension(resultWidth, resultHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDefaultArc.draw(0, 360, canvas);
        mProgressArc.draw(-90, getProgress(), canvas);
        drawProgressLine(canvas);
        drawProgressText(canvas);
    }

    public void setProgress(int progress, boolean isAnimation) {
        progress = Math.max(0, Math.min(100, progress));
        if (!isAnimation) {
            changeProgress(progress);
            return;
        }
        // 带有增长动画
        mHandler.removeCallbacksAndMessages(null);
        Message message = Message.obtain();
        message.arg1 = progress;
        mHandler.sendMessage(message);
    }

    private void drawProgressLine(Canvas canvas) {
        for (int progress = mProgress; progress >= 0; progress--) {
            int height = progressLineR * 2;
            // progress / 100 = y / height
            int y = height - progress * height / 100;

            int leftX;
            int rightX;
            if (progress <= 50) {
                leftX = centerX + (int) (0 - (Math.sqrt(Math.pow(progressLineR, 2) - Math.pow(progressLineR - y, 2))));
                rightX = centerX + (int) (0 + (Math.sqrt(Math.pow(progressLineR, 2) - Math.pow(progressLineR - y, 2))));
            } else {
                leftX = centerX + (int) (0 - (Math.sqrt(Math.pow(progressLineR, 2) - Math.pow(-progressLineR + y, 2))));
                rightX = centerX + (int) (0 + (Math.sqrt(Math.pow(progressLineR, 2) - Math.pow(-progressLineR + y, 2))));
            }
            y = centerY - progressLineR + y;
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            canvas.drawLine(leftX, y, rightX, y, paint);
        }
    }

    private void changeProgress(int progress) {
        mProgress = progress;
        requestLayout();
        invalidate();
    }

    private void drawProgressText(Canvas canvas) {
        Paint paint = new Paint();
        paint.setTextAlign(Paint.Align.CENTER);
        paint.setTextSize(50);
        canvas.drawText(mProgress + "%", centerX, centerY, paint);
    }


    private float getProgress() {
        mProgress = Math.max(0, Math.min(100, mProgress));
        return mProgress * 360 / 100;
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private class CircularArc {

        private Paint mPaint; // 画笔
        private RectF mRectF;

        public CircularArc() {
            mPaint = new Paint();
            mPaint.setColor(Color.BLACK);
            mRectF = new RectF();
        }

        public void draw(float fromArc, float toArc, Canvas canvas) {
            float strokeWidth = mPaint.getStrokeWidth();
            float left = mRectF.left + strokeWidth;
            float top = mRectF.top + strokeWidth;
            float right = mRectF.right - strokeWidth;
            float bottom = mRectF.bottom - strokeWidth;
            mRectF.set(left, top, right, bottom);
            canvas.drawArc(mRectF, fromArc, toArc, false, mPaint);
        }

        public Paint getPaint() {
            return mPaint;
        }

        public void setPaint(Paint paint) {
            this.mPaint = paint;
        }

        public RectF getRectF() {
            return mRectF;
        }

        public void setRectF(RectF rectF) {
            this.mRectF = rectF;
        }
    }
}
