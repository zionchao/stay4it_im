package com.kevin.im.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kevin.im.R;
import com.kevin.im.entities.Tab;

/**
 * Created by zhangchao_a on 2016/11/23.
 */

public class TabView extends RelativeLayout {

    private ImageView mTabIconImg;
    private TextView mTabIconInfo;

    public TabView(Context context) {
        super(context);
        initView(context);
    }

    public TabView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(Context context) {
        LayoutInflater.from(context).inflate(R.layout.widget_tab,this);
        mTabIconImg=(ImageView)findViewById(R.id.mTabIconImg);
        mTabIconInfo=(TextView)findViewById(R.id.mTabIconInfo);

    }

    public void initData(Tab tab){
        mTabIconImg.setImageResource(tab.getIconResId());
        mTabIconInfo.setText(tab.getInfoResId());

    }

    public void notifyDataChanged(int number){

    }
}
