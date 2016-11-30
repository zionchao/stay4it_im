package com.kevin.im.widget.chat.emo;

/**
 * Created by zhangchao_a on 2016/11/7.
 */

public interface OnEmoClickListener {
    void onCustomEmoClick(String group_id,String emo_name);
    void onDeleteButtonClick();
    void onEmoSendClick();

    void onNormalEmoClick(String emo, int resId);
}
