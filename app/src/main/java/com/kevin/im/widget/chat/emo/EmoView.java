package com.kevin.im.widget.chat.emo;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kevin.im.R;


/**
 * Created by zhangchao_a on 2016/11/7.
 */

public class EmoView extends LinearLayout implements View.OnClickListener,ViewPager.OnPageChangeListener, EmoGroupView.OnEmoGroupChangeListener {
    private EmoGroupView mEmoGroupView;
    private EmoDotView mEmoDotView;
    private Button mEmoSendBtn;
    private ViewPager mEmoViewPager;
    private EmoPagerAdapter adapter;
    private OnEmoClickListener listener;
    private Context context;
    private int groupIndex;

    public EmoView(Context context) {
        super(context);
        initView(context);
    }

    public EmoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public EmoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(Context context) {
        this.context=context;
        LayoutInflater.from(context).inflate(R.layout.widget_container_view,this);
        mEmoDotView=(EmoDotView)findViewById(R.id.mEmoDotView);
//        mEmoDotView.notifyDataChanged(0,5);
        mEmoSendBtn=(Button)findViewById(R.id.mEmoSendBtn);
        mEmoSendBtn.setOnClickListener(this);
        mEmoViewPager=(ViewPager)findViewById(R.id.mEmoViewPager);
        adapter=new EmoPagerAdapter();
        mEmoViewPager.setAdapter(adapter);
//        mEmoViewPager.setOnPageChangeListener(this);
        mEmoViewPager.addOnPageChangeListener(this);

        mEmoGroupView=(EmoGroupView)findViewById(R.id.mEmoGroupView);
        mEmoGroupView.initData(this,0);

    }

    public void initData(OnEmoClickListener listener){
        this.listener=listener;
//        onPageSelected(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.mEmoSendBtn:
                listener.onEmoSendClick();
                break;
        }
    }


    class EmoPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return EmoGroupManager.getInstance().getTotalPage();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view==object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            EmoPage page=EmoGroupManager.getInstance().getEmoPage(position);
            EmoGroup group=EmoGroupManager.getInstance().getEmoGroup(page.groupIndex);
            switch (group.type){
                case normal:
                    EmoNormalGrid normal= new EmoNormalGrid(context);
                    normal.initData(page.innerIndex,listener);
                    container.addView(normal);
                    return normal;
                case custom:
                    EmoCustomGrid custom= new EmoCustomGrid(context);
                    custom.initData(group,page.innerIndex,listener);
                    container.addView(custom);
                    return custom;
                default:
                    return null;
            }

        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
            container.removeView((View) object);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int pageIndex) {
//        int curGroupPosition=0;
//        int count=0;
        EmoPage page=EmoGroupManager.getInstance().getEmoPage(pageIndex);
        mEmoDotView.notifyDataChanged(page.innerIndex,page.groupCount);
        if (page.groupIndex!=groupIndex){
            mEmoGroupView.notifyDataChanged(page.groupIndex);
            groupIndex=page.groupIndex;
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onGroupChanged(int groupIndex) {
        EmoGroup group=EmoGroupManager.getInstance().getEmoGroup(groupIndex);
        this.groupIndex=groupIndex;
        mEmoViewPager.setCurrentItem(group.startIndex);
        mEmoDotView.notifyDataChanged(0,group.page);

//        if (position==1)
//        {
//            mEmoViewPager.setCurrentItem(0);
//        }else
//            mEmoViewPager.setCurrentItem(5);

    }
}
