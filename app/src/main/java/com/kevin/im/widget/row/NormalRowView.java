package com.kevin.im.widget.row;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kevin.im.R;


/**
 * Created by zhangchao_a on 2016/11/3.
 */

public class NormalRowView extends BaseRowView implements View.OnClickListener {

    private final Context context;
    private ImageView mWidgetRowIconImg;
    private TextView mWidgetRowLabel;
//    private ImageView mWidgetRowActionImg;
    private OnRowChangeListener listener;
    private NormalRowDescriptor descriptor;

    public NormalRowView(Context context) {
        super(context);
        this.context=context;
        initView();
    }

    public NormalRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView();


    }

    public NormalRowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        initView();

    }

    private void initView() {

        LayoutInflater.from(context).inflate(R.layout.widget_general_row,this);
        mWidgetRowIconImg=(ImageView)findViewById(R.id.mWidgetRowIconImg);
        mWidgetRowLabel=(TextView)findViewById(R.id.mWidgetRowLabel);
//        mWidgetRowActionImg=(ImageView)findViewById(R.id.mWidgetRowActionImg);

//        setOnClickListener(this);
    }

//    public void initData(int iconResId, String name, RowActionEnum action,OnRowChangeListener listener)
//    {
//        this.action=action;
//        this.listener=listener;
//        mWidgetRowIconImg.setBackgroundResource(iconResId);
//        mWidgetRowLabel.setText(name);
//        mWidgetRowActionImg.setImageResource(R.mipmap.ic_row_forward);
//        if (action!=null)
//        {
//            setOnClickListener(this);
//            mWidgetRowActionImg.setVisibility(View.VISIBLE);
//            setBackgroundResource(R.drawable.widgets_general_row_selector);
//        }else
//        {
//            setBackgroundColor(Color.WHITE);
//            mWidgetRowActionImg.setVisibility(View.GONE);
//        }
//
//    }

    @Override
    public void onClick(View v) {
        if (listener!=null)
            listener.onRowChanged(descriptor.action);

    }

    public void initData(BaseRowDescriptor descriptor,OnRowChangeListener listener) {
        this.descriptor=(NormalRowDescriptor) descriptor;
        this.listener=listener;
    }

    public void notifyDataChanged() {
        if (descriptor!=null)
        {
            setVisibility(View.VISIBLE);
            mWidgetRowIconImg.setBackgroundResource(descriptor.iconResId);
            mWidgetRowLabel.setText(descriptor.label);
//            mWidgetRowActionImg.setImageResource(R.mipmap.ic_row_forward);
            if (descriptor.action!=null)
            {
                setOnClickListener(this);
//                mWidgetRowActionImg.setVisibility(View.VISIBLE);
                setBackgroundResource(R.drawable.widgets_general_row_selector);
            }else
            {
                setBackgroundColor(Color.WHITE);
//                mWidgetRowActionImg.setVisibility(View.GONE);
            }
        }else
            setVisibility(View.GONE);
    }
}
