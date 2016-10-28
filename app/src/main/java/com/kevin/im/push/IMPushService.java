package com.kevin.im.push;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.kevin.http.AppException;
import com.kevin.http.JsonCallback;
import com.kevin.http.Request;
import com.kevin.im.IMApplication;
import com.kevin.im.util.Constants;
import com.kevin.im.entities.Message;
import com.kevin.im.util.UrlHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhangchao_a on 2016/10/12.
 */

public class IMPushService extends Service {

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

    private void sendPlainMsg(final Message oldMessage) {
        Request request=new Request(UrlHelper.loadSendMsg());
        request.addHeader("content-type","application/json");
        request.addHeader("Authorization", IMApplication.getToken());

        try {
            JSONObject json=new JSONObject();
            json.put("receiverType","single");
            json.put("receiverId",oldMessage.getReceiverId());
            json.put("receiverName",oldMessage.getReceiver_name());
            json.put("content",oldMessage.getContent());
            json.put("contentType",oldMessage.getContent_type());
            request.content=json.toString();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.setCallback(new JsonCallback<Message>() {
            @Override
            public void onSuccess(Message newMessage) {
                newMessage.setStatus(Message.StatusType.done);
                IMPushManager.getInstance(getApplicationContext()).messageUpdate(oldMessage,newMessage);
            }

            @Override
            public void onFailuer(AppException error) {
                error.printStackTrace();
                oldMessage.setStatus(Message.StatusType.fail);
                IMPushManager.getInstance(getApplicationContext()).messageUpdate(oldMessage,null);
            }
        });
        request.execute();
    }
}
