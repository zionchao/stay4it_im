package com.kevin.im.widget.row;

import java.util.ArrayList;

/**
 * Created by zhangchao_a on 2016/11/4.
 */

public class GroupDescriptor {

    public String title;
    public ArrayList<BaseRowDescriptor> descriptors;

    public GroupDescriptor( ArrayList<BaseRowDescriptor> rowDescriptors) {
        this.descriptors = rowDescriptors;
    }

    public GroupDescriptor(String title, ArrayList<BaseRowDescriptor> rowDescriptors) {
        this.title = title;
        this.descriptors = rowDescriptors;
    }
}
