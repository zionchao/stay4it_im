package com.stay4it_im;

import android.content.Context;
import android.content.Intent;

import com.stay4it_im.entities.Message;

/**
 * Created by zhangchao_a on 2016/10/12.
 */

public class PushManager {

    private final Context context;
    private PushManager mInstance;

    public PushManager(Context context) {
        this.context=context;
    }

    public PushManager getInstance(Context context)
    {
        if (mInstance==null)
        {
            mInstance=new PushManager(context);
        }
        return mInstance;
    }

    public void handlePush(String content)
    {
        Message message=new Message();
        PushChanger.getInstance().notifyChanged(message);
    }

    public void sendMessage(Message msg)
    {
        Intent service=new Intent(context,PushService.class);
         service.putExtra(Constants.KEY_MESSAGE,msg);
         context.startService(service);
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
