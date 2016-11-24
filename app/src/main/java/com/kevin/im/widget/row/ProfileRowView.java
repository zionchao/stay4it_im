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

public class ProfileRowView extends BaseRowView implements View.OnClickListener {

    private final Context context;
    private ImageView mWidgetRowIconImg;
    private TextView mWidgetRowLabel;
    private TextView mWidgetRowDetailLabel;
    private OnRowChangeListener listener;
    private ProfileRowDescriptor descriptor;

    public ProfileRowView(Context context) {
        super(context);
        this.context=context;
        initView();
    }

    public ProfileRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView();


    }

    public ProfileRowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        initView();

    }

    private void initView() {

        LayoutInflater.from(context).inflate(R.layout.widget_profile_row,this);
        mWidgetRowIconImg=(ImageView)findViewById(R.id.mWidgetRowIconImg);
        mWidgetRowLabel=(TextView)findViewById(R.id.mWidgetRowLabel);
        mWidgetRowDetailLabel=(TextView)findViewById(R.id.mWidgetRowDetailLabel);

//        setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if (listener!=null)
            listener.onRowChanged(descriptor.action);

    }

    public void initData(BaseRowDescriptor descriptor,OnRowChangeListener listener) {
        this.descriptor=(ProfileRowDescriptor)descriptor;
        this.listener=listener;
    }

    public void notifyDataChanged() {
        if (descriptor!=null)
        {
            setVisibility(View.VISIBLE);
            mWidgetRowIconImg.setBackgroundResource(R.mipmap.ic_launcher);
            mWidgetRowLabel.setText(descriptor.label);
            mWidgetRowDetailLabel.setText(descriptor.detailLabel);
            if (descriptor.action!=null)
            {
                setOnClickListener(this);
                setBackgroundResource(R.drawable.widgets_general_row_selector);
            }else
            {
                setBackgroundColor(Color.WHITE);
            }
            setVisibility(View.VISIBLE);
        }else
            setVisibility(View.GONE);
    }
}
