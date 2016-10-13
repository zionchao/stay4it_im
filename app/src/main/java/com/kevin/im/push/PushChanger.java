package com.kevin.im.push;

import com.kevin.im.db.MessageController;
import com.kevin.im.entities.Message;

import java.util.Observable;

/**
 * Created by zhangchao_a on 2016/10/12.
 */

public class PushChanger extends Observable {

    private static PushChanger mInstance;

    public static PushChanger getInstance(){
        if (mInstance==null)
        {
            mInstance=new PushChanger();
        }
        return mInstance;
    }

    public void notifyChanged(Message message) {
        MessageController.addOrUpdate(message);
        setChanged();
        notifyObservers(message);
    }
}
