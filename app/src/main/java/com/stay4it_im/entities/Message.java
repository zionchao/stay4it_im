package com.stay4it_im.entities;

import java.io.Serializable;

/**
 * Created by zhangchao_a on 2016/10/12.
 */

public class Message implements Serializable {

    private MessageType content_type;

    public enum MessageType{
        plain,audio,emo,image,location,
    };

    public MessageType getType(){
        return content_type;
    }
}
