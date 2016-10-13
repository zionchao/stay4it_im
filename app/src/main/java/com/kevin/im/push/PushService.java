package com.kevin.im.push;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.kevin.im.util.Constants;
import com.kevin.im.entities.Message;

/**
 * Created by zhangchao_a on 2016/10/12.
 */

public class PushService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Message message= (Message) intent.getSerializableExtra(Constants.KEY_MESSAGE);
        switch (message.getType()){
            case plain:
                sendPlainMsg(message);
                break;
            case audio:
                break;
            case emo:
                break;
            case image:
                break;
            case location:
                break;
            default:
                break;
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private void sendPlainMsg(Message message) {

    }
}
