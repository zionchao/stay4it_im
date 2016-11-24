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

public class MomentsRowView extends BaseRowView implements View.OnClickListener {

    private final Context context;
    private ImageView mWidgetRowIconImg;
    private TextView mWidgetRowLabel;
    private ImageView mWidgetRowLatestImg;
    private OnRowChangeListener listener;
    private MomentsRowDescriptor descriptor;

    public MomentsRowView(Context context) {
        super(context);
        this.context=context;
        initView();
    }

    public MomentsRowView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context=context;
        initView();


    }

    public MomentsRowView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context=context;
        initView();

    }

    private void initView() {

        LayoutInflater.from(context).inflate(R.layout.widget_moments_row,this);
        mWidgetRowIconImg=(ImageView)findViewById(R.id.mWidgetRowIconImg);
        mWidgetRowLabel=(TextView)findViewById(R.id.mWidgetRowLabel);
        mWidgetRowLatestImg=(ImageView)findViewById(R.id.mWidgetRowLatestImg);

//        setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (listener!=null)
            listener.onRowChanged(descriptor.action);

    }

    public void initData(BaseRowDescriptor descriptor,OnRowChangeListener listener) {
        this.descriptor=(MomentsRowDescriptor) descriptor;
        this.listener=listener;
    }

    public void notifyDataChanged() {
        if (descriptor!=null)
        {
            setVisibility(View.VISIBLE);
            mWidgetRowIconImg.setBackgroundResource(descriptor.iconResId);
            mWidgetRowLabel.setText(descriptor.label);
            if (descriptor.latestUrl!=null)
               mWidgetRowLatestImg.setImageResource(R.drawable.more_emoji_store);
            if (descriptor.action!=null)
            {
                setOnClickListener(this);
//                mWidgetRowLatestImg.setVisibility(View.VISIBLE);
                setBackgroundResource(R.drawable.widgets_general_row_selector);
            }else
            {
                setBackgroundColor(Color.WHITE);
//                mWidgetRowLatestImg.setVisibility(View.GONE);
            }
        }else
            setVisibility(View.GONE);
    }
}
