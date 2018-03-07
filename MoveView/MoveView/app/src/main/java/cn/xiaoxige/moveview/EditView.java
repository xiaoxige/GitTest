package cn.xiaoxige.moveview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import cn.xiaoxige.moveview.view.BaseEditView;

/**
 * Created by xiaoxige on 2018/3/6.
 * 用于测试自定义使用
 */

public class EditView extends BaseEditView {

    private TextView tvMsg;
    private Button btnClickMe;

    public EditView(Context context) {
        this(context, null);
    }

    public EditView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EditView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void addDetailView(ViewGroup container) {
        View view = View.inflate(mContext, R.layout.view_edit, null);
        container.addView(view);
    }

    @Override
    protected void initView() {
        super.initView();
        tvMsg = (TextView) findViewById(R.id.tvMsg);
        btnClickMe = (Button) findViewById(R.id.btnClickMe);
    }

    @Override
    protected void registerListener() {
        super.registerListener();

        btnClickMe.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mCallback != null) {
                    mCallback.opt(view, 0);
                }
            }
        });
    }

    public void setMsg(String msg) {
        tvMsg.setText(msg);
    }
}
