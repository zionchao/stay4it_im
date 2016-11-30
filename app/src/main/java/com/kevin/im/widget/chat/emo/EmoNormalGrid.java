package com.kevin.im.widget.chat.emo;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.kevin.im.R;


/**
 * Created by zhangchao_a on 2016/11/7.
 */

public class EmoNormalGrid extends GridView implements AdapterView.OnItemClickListener {
    private OnEmoClickListener listener;
    private int curPosition;
    private int startResId;
    private int endResId;
    private Context context;

    public EmoNormalGrid(Context context) {
        super(context);
        initView(context);
    }

    public EmoNormalGrid(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public EmoNormalGrid(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }

    private void initView(Context context) {
        this.context=context;
        setNumColumns(7);
//        setVerticalSpacing();
        setOnItemClickListener(this);
        setAdapter(new EmoAdapter());
        setSelector(new BitmapDrawable());

    }

    public void initData(int position,OnEmoClickListener listener) {
        this.listener=listener;
        this.curPosition=position;
        startResId= R.drawable.smiley_00+position*20;
//        endResId=R.mipmap.smiley_00+(position+1)*20;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (position!=20)
        {
            int res=position+curPosition*20;
            listener.onNormalEmoClick("smiley_"+(position<10?"0"+position:position),startResId+position);
        }else
        {
            listener.onDeleteButtonClick();
        }
    }

    class EmoAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return 21;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView image=new ImageView(context);
            if (position!=20)
            {
                image.setBackgroundResource(startResId+position);
            }else {
                image.setBackgroundResource(R.drawable.selector_btn_delete);
            }
            return image;

        }
    }

}
