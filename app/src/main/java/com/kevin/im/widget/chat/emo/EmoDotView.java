package com.kevin.im.widget.chat.emo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by zhangchao_a on 2016/11/7.
 */

public class EmoDotView extends View {

    private int width;
    private float density;
    private float radius;
    private float padding;
    private int position;
    private int count;
    private float mStartX;
    private Paint paint;
    private Context context;

    public EmoDotView(Context context) {
        super(context);
        initView(context);

    }

    public EmoDotView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);

    }

    public EmoDotView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);

    }


    private void initView(Context context) {
//        setWillNotDraw(false);
        this.context=context;
        width=getResources().getDisplayMetrics().widthPixels;
        density=getResources().getDisplayMetrics().density;
        radius=3*density;
        padding=10*density;

    }

    public void notifyDataChanged(int position, int count) {
        this.position=position;
        this.count=count;
        this.mStartX=(width-((count-1)*padding+count*radius*2))/2;
        paint=new Paint();
        postInvalidate();
//        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i=0;i<count;i++)
        {
            if (position==i)
                paint.setColor(Color.RED);
            else
                paint.setColor(Color.GRAY);
             canvas.drawCircle(mStartX+i*padding+i*radius*2,radius,radius,paint);
        }
    }

}
