package com.kevin.im.widget.chat.emo;


import com.kevin.im.R;

/**
 * Created by zhangchao_a on 2016/11/17.
 */

public class EmoGroup {
    public int startIndex;
    public int count;
    public int page;
    public String url;
    public String name;
    public String id;
    public EmoType type;
    public int resId;

    public static EmoGroup getDefault()
    {
        EmoGroup group=new EmoGroup();
        group.count=100;
        group.page=5;
        group.type=EmoType.normal;
        group.resId= R.drawable.ic_emoji;
        return group;
    }

    public static EmoGroup getCustom()
    {
        EmoGroup group=new EmoGroup();
        group.count=8;
        group.page=2;
        group.type=EmoType.custom;
        group.resId= R.drawable.ic_emo;
        group.name="em_tusiji";
        group.id="em_tusiji";
        return group;
    }
}
