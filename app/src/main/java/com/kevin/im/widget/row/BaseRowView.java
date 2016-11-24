package com.kevin.im.widget.row;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by ZhangChao on 2016/11/4.
 */

public abstract class BaseRowView extends LinearLayout{

    public BaseRowView(Context context) {
        super(context);
    }

    public BaseRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseRowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public abstract void initData(BaseRowDescriptor descriptor, OnRowChangeListener listener);

    public abstract void notifyDataChanged();
}
