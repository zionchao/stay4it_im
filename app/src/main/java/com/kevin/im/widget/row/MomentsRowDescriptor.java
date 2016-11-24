package com.kevin.im.widget.row;

/**
 * Created by ZhangChao on 2016/11/4.
 */

public class MomentsRowDescriptor extends BaseRowDescriptor{
    public int iconResId;
    public String label;
    public String latestUrl;

    public MomentsRowDescriptor(int iconResId, String label, String latestUrl,RowActionEnum action) {
        this.iconResId = iconResId;
        this.label = label;
        this.latestUrl = latestUrl;
        this.action=action;
    }
}
