package com.kevin.im.widget.chat.emo;

import java.util.ArrayList;

/**
 * Created by zhangchao_a on 2016/11/17.
 */

public class EmoGroupManager {
    private static EmoGroupManager mInstance;
    private ArrayList<EmoPage> mEmoPages=null;
    private ArrayList<EmoGroup> mEmoGroups=null;
    public int totalPage=0;

    public EmoGroupManager() {
        initData();
    }

    public static EmoGroupManager getInstance() {
        if (mInstance==null)
        {
            mInstance=new EmoGroupManager();
        }
        return mInstance;
    }

    private void initData() {
        mEmoGroups=new ArrayList<EmoGroup>();
        mEmoGroups.add(EmoGroup.getDefault());
        mEmoGroups.add(EmoGroup.getCustom());
//        mEmoGroups.add(EmoGroup.getCustom());
        mEmoPages=new ArrayList<EmoPage>();
        EmoPage page=null;
        EmoGroup group=null;
        for (int i=0;i<mEmoGroups.size();i++)
        {
            group=mEmoGroups.get(i);
            group.startIndex=totalPage;
            totalPage+=group.page;
            for (int j=0;j<group.page;j++)
            {
                page=new EmoPage();
                page.totalIndex=mEmoPages.size();
                page.innerIndex=j;
                page.groupIndex=i;
                page.groupCount=group.page;
                this.mEmoPages.add(page);
            }
        }
    }

    public int getTotalPage()
    {
        return mEmoPages.size();
    }

    public EmoGroup getEmoGroup(int groupIndex){
        return mEmoGroups.get(groupIndex);
    }

    public EmoPage getEmoPage(int pageIndex){
        return mEmoPages.get(pageIndex);
    }

    public int getTotalGroupCount(){
        return mEmoGroups.size();
    }
}
