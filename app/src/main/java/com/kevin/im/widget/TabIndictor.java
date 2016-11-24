package com.kevin.im.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kevin.im.entities.Tab;
import com.kevin.im.util.TextUtil;

import java.util.ArrayList;

/**
 * Created by zhangchao_a on 2016/11/23.
 */

public class TabIndictor extends LinearLayout implements View.OnClickListener {

    private int mTabIndex=-1;
    private final static int ID_PREFIX=10000;
    private OnTabClickListener listener;
    private LayoutParams mTabParams;
    private int mTabSize;

    public TabIndictor(Context context) {
        super(context);
        initView(context);
    }

    public TabIndictor(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public TabIndictor(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(Context context) {
        setOrientation(HORIZONTAL);
        mTabParams=new LayoutParams(new LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT));
        mTabParams.weight=1.0f;
    }

    public void initData(ArrayList<Tab> tabs){
        if (!TextUtil.isValidate(tabs)){
            throw new IllegalArgumentException("the tabs should not be 0");
        }
        mTabSize=tabs.size();
        TabView tab=null;
        for (int i=0;i<mTabSize;i++)
        {
            tab=new TabView(getContext());
            tab.setId(ID_PREFIX+i);
            tab.setOnClickListener(this);
            tab.initData(tabs.get(i));
            addView(tab,mTabParams);
        }
    }

    public void setOnTabClickListener(OnTabClickListener listener)
    {
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        int index=v.getId()-ID_PREFIX;
        if (listener!=null&&mTabIndex!=index){
            listener.onTabClick(v.getId()-ID_PREFIX);
            v.setSelected(true);
            if (mTabIndex!=-1)
            {
                View old=findViewById(ID_PREFIX+mTabIndex);
                old.setSelected(false);
            }
            mTabIndex=index;
        }
    }

    public interface OnTabClickListener{
        void onTabClick(int index);
    }

    public void setCurrentTab(int i){
        if (i==mTabIndex){
            return;
        }
        View view=findViewById(ID_PREFIX+i);
        onClick(view);
    }
}
