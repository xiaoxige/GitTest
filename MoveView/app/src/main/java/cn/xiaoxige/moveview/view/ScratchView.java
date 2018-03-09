package com.pangpangzhu.commonlibrary.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by xiaoxige on 2018/3/8.
 * 刮刮卡
 */

public class ScratchView extends View {

    private Context mContext;

    private Bitmap mFrontBit;
    private Canvas mFontCanvas;
    private Paint mFontPaint;
    private Path mLinePath;

    private Paint mAnswerPaint;

    private int mViewWidth;
    private int mViewHeight;
    private int mActualWidth;
    private int mActualHeight;

    private String mAnswerMsg;
    private String mFontMsg;
    private int mFontStrokeWidth;
    private int mFontBackground;
    private int mAnswerTextSize;
    private int mAnswerTextColor;
    private int mFontTextSize;
    private int mFontTextColor;
    private int mSensitive;

    /**
     * 是否刮卡成功
     */
    private boolean mIsScratch;
    /**
     * 是否可以刮卡
     */
    private boolean mIsCanScratch;
    private float mX;
    private float mY;

    private onScratchListener mListener;

    public ScratchView(Context context) {
        this(context, null);
    }

    public ScratchView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScratchView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;

        init();
    }

    private void init() {
        mSensitive = 15;
        mIsScratch = false;
        mFontStrokeWidth = 40;
        mAnswerTextSize = 60;
        mFontTextSize = 60;
        mFontBackground = Color.GRAY;
        mFontTextColor = Color.WHITE;
        mAnswerTextColor = Color.BLACK;
        mIsCanScratch = true;
        mLinePath = new Path();
        mFontPaint = createFontPaint();
        mAnswerPaint = createAnswerPaint();
    }

    private Paint createFontPaint() {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setDither(true);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeWidth(mFontStrokeWidth);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        return paint;
    }

    private Paint createAnswerPaint() {
        Paint paint = new Paint();
        paint.setColor(mAnswerTextColor);
        paint.setAntiAlias(true);
        paint.setTextSize(mAnswerTextSize);
        paint.setTextAlign(Paint.Align.CENTER);
        return paint;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode != MeasureSpec.EXACTLY || heightMode != MeasureSpec.EXACTLY) {
//            throw new RuntimeException("ScratchView'height and width is not to make clear");
        }

        mViewWidth = MeasureSpec.getSize(widthMeasureSpec);
        mViewHeight = MeasureSpec.getSize(heightMeasureSpec);
        mActualWidth = mViewWidth - getPaddingLeft() - getPaddingRight();
        mActualHeight = mViewHeight - getPaddingTop() - getPaddingBottom();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // 画最底层的中奖部分
        drawAnswer(canvas);
        if (!mIsScratch) {
            // 画前景
            drawFont(canvas);
        }
    }

    public void reset() {
        reset(mFontMsg);
    }

    public void reset(String fontMsg) {
        mIsScratch = false;
        mFontMsg = fontMsg;
        mFrontBit = null;
        mLinePath = new Path();
        invalidate();
    }

    public void setAnswerMsg(String ansWerMsg) {
        mAnswerMsg = ansWerMsg;
    }

    public void setFrontMsg(String fontMsg) {
        reset(fontMsg);
    }

    public void setSensitive(int sensitive) {
        sensitive = Math.max(0, Math.min(100, sensitive));
        mSensitive = sensitive;
        invalidate();
    }

    public void setFontStrokeWidth(int width) {
        mFontStrokeWidth = width;
        if (mFontPaint == null) {
            mFontPaint = createFontPaint();
        }
        mFontPaint.setStrokeWidth(width);
        reset(mFontMsg);
    }

    public void setFontBackground(@ColorInt int color) {
        mFontBackground = color;
        reset(mFontMsg);
    }

    public void setAnswerTextSize(int size) {
        mAnswerTextSize = size;
        if (mAnswerPaint == null) {
            mAnswerPaint = createAnswerPaint();
        }
        mAnswerPaint.setTextSize(size);
        invalidate();
    }

    public void setAnswerTextColor(@ColorInt int color) {
        mAnswerTextColor = color;
        if (mAnswerPaint == null) {
            mAnswerPaint = createAnswerPaint();
        }
        mAnswerPaint.setColor(color);
        invalidate();
    }

    public void setFontTextSize(int size) {
        mFontTextSize = size;
        reset(mFontMsg);
    }

    public void setFontTextColor(@ColorInt int color) {
        mFontTextColor = color;
        reset(mFontMsg);
    }

    public void setIsCanScratch(boolean isCanScratch) {
        mIsCanScratch = isCanScratch;
        invalidate();
    }

    private void drawAnswer(Canvas canvas) {
        if (!TextUtils.isEmpty(mAnswerMsg)) {
            Rect rect = new Rect();
            mAnswerPaint.getTextBounds(mAnswerMsg, 0, mAnswerMsg.length(), rect);
            int textHeight = rect.bottom - rect.top;
            canvas.drawText(mAnswerMsg, mActualWidth / 2,
                    (mActualHeight + textHeight) / 2, mAnswerPaint);
        }
    }

    private void drawFont(Canvas canvas) {
        if (mFrontBit == null) {
            mFrontBit = Bitmap.createBitmap(mActualWidth,
                    mActualHeight,
                    Bitmap.Config.ARGB_8888);
            mFontCanvas = new Canvas(mFrontBit);
            mFontCanvas.drawColor(mFontBackground);
            Paint paint = new Paint();
            paint.setColor(mFontTextColor);
            paint.setTextSize(mFontTextSize);
            paint.setTextAlign(Paint.Align.CENTER);
            if (!TextUtils.isEmpty(mFontMsg)) {
                Rect rect = new Rect();
                paint.getTextBounds(mFontMsg, 0, mFontMsg.length(), rect);
                int textHeight = rect.bottom - rect.top;
                mFontCanvas.drawText(mFontMsg,
                        mActualWidth / 2,
                        (mActualHeight + textHeight) / 2, paint);
            }
        }

        mFontCanvas.drawPath(mLinePath, mFontPaint);

        canvas.drawBitmap(mFrontBit, 0, 0, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        if (!mIsCanScratch) {
            return false;
        }
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mX = event.getX();
                mY = event.getY();
                mLinePath.moveTo(mX, mY);
                break;
            case MotionEvent.ACTION_MOVE:
                mX = event.getX();
                mY = event.getY();
                mLinePath.lineTo(mX, mY);
                break;
            case MotionEvent.ACTION_UP:
                if (!mIsScratch && mIsCanScratch) {
                    HandlerScratch();
                }
                break;
            default:
                break;
        }
        invalidate();
        return true;
    }

    private void HandlerScratch() {
        if (mFrontBit == null) {
            return;
        }
        int width = mFrontBit.getWidth();
        int height = mFrontBit.getHeight();

        float wipeArea = 0;
        float totalArea = width * height;

        Bitmap bitmap = mFrontBit;

        int[] mPixels = new int[width * height];

        /**
         * 拿到所有的像素信息
         */
        bitmap.getPixels(mPixels, 0, width, 0, 0, width, height);

        /**
         * 遍历统计擦除的区域
         */
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int index = i + j * width;
                if (mPixels[index] == 0) {
                    wipeArea++;
                }
            }
        }

        /**
         * 根据所占百分比，进行一些操作
         */
        if (wipeArea > 0 && totalArea > 0) {
            int percent = (int) (wipeArea * 100 / totalArea);
            if (percent > mSensitive) {
                mIsScratch = true;
                if (mListener != null) {
                    mListener.scratched(this, mAnswerMsg);
                }
                invalidate();
            }
        }
    }

    public void setListener(onScratchListener listener) {
        this.mListener = listener;
    }

    public interface onScratchListener {
        void scratched(View v, String answer);
    }
}
