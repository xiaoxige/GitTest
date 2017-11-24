package com.prolificinteractive.materialcalendarview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.prolificinteractive.materialcalendarview.MaterialCalendarView.ShowOtherDates;
import com.prolificinteractive.materialcalendarview.format.DayFormatter;

import java.util.LinkedList;
import java.util.List;

import static com.prolificinteractive.materialcalendarview.MaterialCalendarView.showDecoratedDisabled;
import static com.prolificinteractive.materialcalendarview.MaterialCalendarView.showOtherMonths;
import static com.prolificinteractive.materialcalendarview.MaterialCalendarView.showOutOfRange;

/**
 * Display one day of a {@linkplain MaterialCalendarView}
 */
@SuppressLint("ViewConstructor")
class DayViewGroup extends FrameLayout {

    private static final int[] STATE_DISABLE = {
            R.attr.state_disable
    };
    private static final int[] STATE_SELECTABLE = {
            R.attr.state_selectable
    };

    private static final int[] STATE_BOARDABLE = {
            R.attr.state_boardable
    };

    private boolean mIsSelected;
    private boolean mIsDisabled;
    private boolean mIsBoardable;

    private CalendarDay date;
    private int selectionColor = Color.BLACK;

    private final int fadeTime;
    private Drawable customBackground = null;
    private Drawable selectionDrawable;
    private Drawable mCircleDrawable;
    private DayFormatter formatter = DayFormatter.DEFAULT;

    private boolean isInRange = true;
    private boolean isInMonth = true;
    private boolean isDecoratedDisabled = false;
    @ShowOtherDates
    private int showOtherDates = MaterialCalendarView.SHOW_DEFAULTS;

    private TextView mDayView;
    private TextView mDayDescView;

    public DayViewGroup(Context context, CalendarDay day) {
        super(context);

        View.inflate(context, R.layout.calendar_dayview, this);
        mDayView = (TextView) findViewById(R.id.tvDay);
        mDayDescView = (TextView) findViewById(R.id.tvDescNum);

        fadeTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        //        setSelectionColor(this.selectionColor);
        setBackgroundResource(R.drawable.materialcalendarview_bg_selector);

        mDayView.setGravity(Gravity.CENTER);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            setTextAlignment(TEXT_ALIGNMENT_CENTER);
        }

        setDay(day);
    }

    public void setDay(CalendarDay date) {
        this.date = date;
        mDayView.setText(getLabel());
    }

    /**
     * Set the new label formatter and reformat the current label. This preserves current spans.
     *
     * @param formatter new label formatter
     */
    public void setDayFormatter(DayFormatter formatter) {
        this.formatter = formatter == null ? DayFormatter.DEFAULT : formatter;
        CharSequence currentLabel = mDayView.getText();
        Object[] spans = null;
        if (currentLabel instanceof Spanned) {
            spans = ((Spanned) currentLabel).getSpans(0, currentLabel.length(), Object.class);
        }
        SpannableString newLabel = new SpannableString(getLabel());
        if (spans != null) {
            for (Object span : spans) {
                newLabel.setSpan(span, 0, newLabel.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
        }
        mDayView.setText(newLabel);
    }

    @NonNull
    public String getLabel() {
        return formatter.format(date);
    }

    public void setSelectionColor(int color) {
        this.selectionColor = color;
    }

    public CalendarDay getDate() {
        return date;
    }

    private void setEnabled() {
        boolean enabled = isInMonth && isInRange;
        super.setEnabled(isInRange);

        boolean showOtherMonths = showOtherMonths(showOtherDates);
        boolean showOutOfRange = showOutOfRange(showOtherDates) || showOtherMonths;
        boolean showDecoratedDisabled = showDecoratedDisabled(showOtherDates);

        boolean shouldBeVisible = enabled;

        if (!isInMonth && showOtherMonths) {
            shouldBeVisible = true;
        }

        if (!isInRange && showOutOfRange) {
            shouldBeVisible |= isInMonth;
        }

        if (isDecoratedDisabled && showDecoratedDisabled) {
            shouldBeVisible |= isInMonth && isInRange;
        }

        if (!isInMonth && shouldBeVisible) {
            mDayView.setTextColor(mDayView.getTextColors().getColorForState(
                    new int[]{-android.R.attr.state_enabled}, Color.GRAY));

            setBackgroundColor(getResources().getColor(R.color.materialcalendarview_color_disable));
            mDayView.setTextColor(getResources().getColor(R.color.materialcalendarview_color_grey_bg));
        }
        setVisibility(shouldBeVisible ? View.VISIBLE : View.INVISIBLE);
    }

    protected void setupSelection(@ShowOtherDates int showOtherDates, boolean inRange, boolean inMonth) {
        this.showOtherDates = showOtherDates;
        this.isInMonth = inMonth;
        this.isInRange = inRange;
        setEnabled();
    }

    @Override
    public void setSelected(boolean isSelected) {
        this.mIsSelected = isSelected;
        refreshDrawableState();
    }

    public boolean getSelected() {
        return mIsSelected;
    }

    public void setDisabled(boolean isDisabled) {
        this.mIsDisabled = isDisabled;
        refreshDrawableState();
    }

    public boolean getDisabled() {
        return mIsDisabled;
    }

    public boolean ismIsBoardable() {
        return mIsBoardable;
    }

    public void setmIsBoardable(boolean mIsBoardable) {
        this.mIsBoardable = mIsBoardable;
        refreshDrawableState();
    }

    /**
     * @param facade apply the facade to us
     */
    void applyFacade(DayViewFacade facade) {
        this.isDecoratedDisabled = facade.areDaysDisabled();

        mDayDescView.setVisibility(GONE);
        LinkedList<DayViewFacade.DayViewDesc> dayViewDescs = facade.getDayViewDescs();
        if (dayViewDescs != null) {
            for (DayViewFacade.DayViewDesc dayDescNum : dayViewDescs) {
                int num = dayDescNum.getmDescNum();
                if (num > 0) {
                    mDayDescView.setText("" + num);
                    mDayDescView.setVisibility(VISIBLE);
                }
            }
        }


        mDayView.setTextColor(facade.mTextColor);
        this.mIsSelected = facade.mIsSelected;
        this.mIsDisabled = facade.mIsDisabled;
        this.mIsBoardable = facade.mIsBoradable;

        setEnabled();
        refreshDrawableState();


        // Facade has spans
        List<DayViewFacade.Span> spans = facade.getSpans();
        if (!spans.isEmpty()) {
            String label = getLabel();
            SpannableString formattedLabel = new SpannableString(getLabel());
            for (DayViewFacade.Span span : spans) {
                formattedLabel.setSpan(span.span, 0, label.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            }
            mDayView.setText(formattedLabel);
        }
        // Reset in case it was customized previously
        else {
            mDayView.setText(getLabel());
        }
    }

    public TextView getDayView() {
        return mDayView;
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
        final int[] drawableState = super.onCreateDrawableState(extraSpace + 3);

        if (mIsDisabled) {
            mergeDrawableStates(drawableState, STATE_DISABLE);
        }

        if (mIsSelected) {
            mergeDrawableStates(drawableState, STATE_SELECTABLE);
        }

        if (mIsBoardable) {
            mergeDrawableStates(drawableState, STATE_BOARDABLE);
        }

        return drawableState;
    }
}
