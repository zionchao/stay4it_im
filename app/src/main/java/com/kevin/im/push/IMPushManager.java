package com.kevin.im.push;

import android.content.Context;

import com.kevin.im.entities.Message;

/**
 * Created by zhangchao_a on 2016/10/12.
 */

public class IMPushManager {

    private final Context context;
    private static IMPushManager mInstance;

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

    public void handlePush(String content)
    {
        Message message=Message.test("0001","me","you");
//        Message message=new Message();
        PushChanger.getInstance().notifyChanged(message);
    }

    public void sendMessage(Message msg)
    {
//         Intent service=new Intent(context,IMPushService.class);
//         service.putExtra(Constants.KEY_MESSAGE,msg);
//         context.startService(service);
           msg.setStatus(Message.StatusType.ing);
           PushChanger.getInstance().notifyChanged(msg);
           msg.setStatus(Message.StatusType.done);
          PushChanger.getInstance().notifyChanged(msg);

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
