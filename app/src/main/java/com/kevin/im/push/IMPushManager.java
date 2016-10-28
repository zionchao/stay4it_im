package com.kevin.im.push;

import android.content.Context;
import android.content.Intent;

import com.google.gson.Gson;
import com.kevin.im.entities.Message;
import com.kevin.im.util.Constants;

/**
 * Created by zhangchao_a on 2016/10/12.
 */

public class IMPushManager {

    private final Context context;
    private static IMPushManager mInstance;
    private Gson gson=new Gson();

    private IMPushManager(Context context) {

        this.context=context;
    }

    public static IMPushManager getInstance(Context context)
    {
        if (mInstance==null)
        {
            mInstance=new IMPushManager(context);
        }
        return mInstance;
    }

    public void handlePush(Message message) {
        PushChanger.getInstance().notifyChanged(message);
    }

    public void messageUpdate(Message oldMessage, Message newMessage) {
        PushChanger.getInstance().notifyChanged(oldMessage,newMessage);
    }

    public void handlePush(String content)
    {
//        Message message=Message.test("0001","me","you");
//        Message message=new Message();
        Message message=gson.fromJson(content,Message.class);
        PushChanger.getInstance().notifyChanged(message);
    }

    public void sendMessage(Message msg)
    {
         msg.setStatus(Message.StatusType.ing);
        PushChanger.getInstance().notifyChanged(msg);

         Intent service=new Intent(context,IMPushService.class);
         service.putExtra(Constants.KEY_MESSAGE,msg);
         context.startService(service);
//           msg.setStatus(Message.StatusType.ing);
//           PushChanger.getInstance().notifyChanged(msg);
//           msg.setStatus(Message.StatusType.done);
//          PushChanger.getInstance().notifyChanged(msg);

    }

    public void addObservers(PushWatcher watcher)
    {
         PushChanger.getInstance().addObserver(watcher);
    }

    public void removeObservers(PushWatcher watcher)
    {
        PushChanger.getInstance().deleteObserver(watcher);
    }

    public void removeObservers()
    {
        PushChanger.getInstance().deleteObservers();
    }



}
