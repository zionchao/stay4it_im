package com.kevin.im.widget.chat.emo;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ImageSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zhangchao_a on 2016/11/20.
 */

public class EmoParse {

    private static float density;

    public static SpannableStringBuilder parseEmo(Context context, String content){
        //[smiley_00]
        SpannableStringBuilder builder=new SpannableStringBuilder(content);
        Pattern pattern=Pattern.compile("\\[smiley_(.*?)\\]");
        Matcher matcher=pattern.matcher(content);
        density=context.getResources().getDisplayMetrics().density;
        int emoSize=(int)(25*density);
        String emo=null;
        int id=0;
        Drawable drawable=null;
        while (matcher.find())
        {
            emo=matcher.group();
            emo=emo.substring(1,emo.length()-1);
            id=context.getResources().getIdentifier(emo,"drawable",context.getPackageName());
            if (id!=0)
            {
                drawable=context.getResources().getDrawable(id);
                drawable.setBounds(0,0,emoSize,emoSize);
                ImageSpan span=new ImageSpan(drawable);
                builder.setSpan(span,matcher.start(),matcher.end(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            }
        }
        return builder;
    }
}
