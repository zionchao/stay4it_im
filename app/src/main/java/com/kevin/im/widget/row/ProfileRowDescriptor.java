package com.kevin.im.widget.row;

/**
 * Created by ZhangChao on 2016/11/4.
 */

public class ProfileRowDescriptor extends BaseRowDescriptor{
    public String imgUrl;
    public String label;
    public String detailLabel;

    public ProfileRowDescriptor(String imgUrl, String label, String detailLabel,RowActionEnum action) {
        this.imgUrl = imgUrl;
        this.label = label;
        this.detailLabel = detailLabel;
        this.action=action;
    }
}
