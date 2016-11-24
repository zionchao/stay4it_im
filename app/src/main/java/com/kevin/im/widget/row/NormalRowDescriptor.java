package com.kevin.im.widget.row;

/**
 * Created by ZhangChao on 2016/11/3.
 */

public class NormalRowDescriptor extends BaseRowDescriptor{
    public int iconResId;
    public String label;
    public RowActionEnum action;

    public NormalRowDescriptor(int iconResId, String label, RowActionEnum action) {
        this.iconResId = iconResId;
        this.label = label;
        this.action = action;
    }
}
