package com.kevin.im.widget.row;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

/**
 * Created by zhangchao_a on 2016/11/4.
 */

public class ContainerView extends LinearLayout {

    private Context context;
    private ArrayList<GroupDescriptor> descriptors;
    private OnRowChangeListener listener;

    public ContainerView(Context context) {
        super(context);
        initView(context);
    }

    public ContainerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public ContainerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(Context context) {
        setOrientation(VERTICAL);
//        setLayoutParams(new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        this.context=context;
    }

    public void initData(ArrayList<GroupDescriptor> descriptors,OnRowChangeListener listener){
        this.descriptors=descriptors;
        this.listener=listener;

    }

    public  void notifyDataChanged()
    {
        if(descriptors!=null&&descriptors.size()>0)
        {
            GroupView group=null;
            float density=context.getResources().getDisplayMetrics().density;
            LayoutParams params=new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            params.topMargin=(int)(20*density);
            for (GroupDescriptor descriptor:descriptors)
            {
                group=new GroupView(context);
                group.initData(descriptor,listener);
                group.notifyDataChanged();
                addView(group,params);
//                addView(group);
            }
            setVisibility(View.VISIBLE);
        }else
        {
            setVisibility(View.GONE);
        }
    }
}
