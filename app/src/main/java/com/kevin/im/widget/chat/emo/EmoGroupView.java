package com.kevin.im.widget.chat.emo;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.kevin.im.R;


/**
 * Created by zhangchao_a on 2016/11/7.
 */

public class EmoGroupView extends HorizontalScrollView implements RadioGroup.OnCheckedChangeListener, View.OnClickListener {

    private RadioGroup mRadioGroup;
    private Context context;
    private OnEmoGroupChangeListener listener;
    private RadioButton mRadioButton;
    private int totalGroupCount;

    public EmoGroupView(Context context) {
        super(context);
        initView(context);
    }

    public EmoGroupView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public EmoGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(Context context) {
        this.context=context;
        mRadioGroup=new RadioGroup(context);
        mRadioGroup.setOnCheckedChangeListener(this);
        mRadioGroup.setOrientation(LinearLayout.HORIZONTAL);
        addView(mRadioGroup);

    }

    public void initData(OnEmoGroupChangeListener listener,int defaultCheckedIndex)
    {
        this.listener=listener;
        totalGroupCount=EmoGroupManager.getInstance().getTotalGroupCount();
        RadioButton radio=null;
        float density=getResources().getDisplayMetrics().density;
        int resId=0;
        for (int i=0;i<totalGroupCount;i++)
        {
            radio=new RadioButton(context);
            radio.setPadding((int)(density * 15), (int)(density * 5), (int)(density * 15), (int)(density * 5));
            resId=EmoGroupManager.getInstance().getEmoGroup(i).resId;
            radio.setCompoundDrawablesWithIntrinsicBounds(0,resId,0,0);
            radio.setBackgroundResource(R.drawable.selector_emo_group);
            radio.setBackground(new BitmapDrawable());
            radio.setId(i);
            radio.setGravity(Gravity.LEFT);
            if (i==defaultCheckedIndex){
                radio.setChecked(true);
            }
            radio.setOnClickListener(this);
            mRadioGroup.addView(radio);

        }

//        RadioButton button1=new RadioButton(context);
//        RadioButton button2=new RadioButton(context);
////        button1.setBackgroundDrawable(null);
//        button1.setBackground(null);
//        button2.setBackground(null);
//        button1.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.smiley_00, 0,0);
//        button2.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_emo, 0,0);
//        button1.setChecked(true);
//        button1.setText("Emoji");
//        button2.setText("Custom");
//        button1.setId(0);
//        button2.setId(1);
//        mRadioGroup.addView(button1);
//        mRadioGroup.addView(button2);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
         listener.onGroupChanged(checkedId);
    }

    public void notifyDataChanged(int groupIndex) {
        mRadioButton=(RadioButton)mRadioGroup.getChildAt(groupIndex);
        mRadioButton.setChecked(true);
    }

    @Override
    public void onClick(View v) {
        listener.onGroupChanged(v.getId());
    }

    interface OnEmoGroupChangeListener{
        void onGroupChanged(int position);
    }
}
