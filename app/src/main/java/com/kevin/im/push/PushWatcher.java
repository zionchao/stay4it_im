package com.kevin.im.push;

import com.kevin.im.entities.Message;

import java.util.Observable;
import java.util.Observer;

/**
 * Created by zhangchao_a on 2016/10/12.
 */

public class PushWatcher implements Observer {
    @Override
    public void update(Observable observable, Object data) {
       if (data instanceof Message)
       {
           onMessageReceived((Message)data);
       }else if (data instanceof Message[])
       {
           onMessageUpdated(((Message[])data)[0],((Message[])data)[1]);
       }
    }



    public void onMessageReceived(Message message) {
    }

    public void onMessageUpdated(Message oldMessage, Message newMessage) {
    }
}
