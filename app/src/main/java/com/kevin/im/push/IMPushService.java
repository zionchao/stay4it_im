package com.kevin.im.push;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.kevin.im.IMApplication;
import com.kevin.im.entities.Message;
import com.kevin.im.net.AppException;
import com.kevin.im.net.JsonCallback;
import com.kevin.im.net.Request;
import com.kevin.im.net.RequestManager;
import com.kevin.im.net.UploadUtil;
import com.kevin.im.util.Constants;
import com.kevin.im.util.Trace;
import com.kevin.im.util.UrlHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.OutputStream;

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
                sendMediaMsg(message);
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

    private void sendMediaMsg(final Message oldMessage) {
        Request request=new Request(UrlHelper.loadSendMediaMsg());
        request.addHeader("Authorization",IMApplication.getToken());
        request.addHeader("Connection","Keep-Alive");
        request.addHeader("Charset","UTF_8");
        request.addHeader("Content-Type","multipart/form-data;boundary=7d4a6d158c9");
        request.setCallback(new JsonCallback<Message>() {

            @Override
            public boolean onCustomOutput(OutputStream out) throws AppException {

                JSONObject json = new JSONObject();
                try {
                    json.put("receiverType", "single");
                    json.put("receiverId", oldMessage.getReceiverId());
                    json.put("receiverName", oldMessage.getReceiver_name());
                    json.put("contentType", oldMessage.getContent_type());
                    json.put("content", oldMessage.getContent());
                    String postContent = json.toString();
                    UploadUtil.upload(out, postContent, oldMessage.getAttachments());

                } catch (JSONException e) {
                    throw new AppException(AppException.ErrorType.JSONException, "upload post content json error");
                } catch (AppException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            public void onSuccess(Message newMessage) {
                if (newMessage != null) {
                    Trace.d("message send success:" + newMessage.toString());
                    newMessage.setStatus(Message.StatusType.done);
                    IMPushManager.getInstance(getApplicationContext()).messageUpdate(oldMessage, newMessage);
                } else {
                    oldMessage.setStatus(Message.StatusType.fail);
                    IMPushManager.getInstance(getApplicationContext()).messageUpdate(oldMessage, null);
                }
            }

            @Override
            public void onFailuer(AppException exception) {
                exception.printStackTrace();
                oldMessage.setStatus(Message.StatusType.fail);
                IMPushManager.getInstance(getApplicationContext()).messageUpdate(oldMessage, null);
            }
        });

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
//        request.execute();
        RequestManager.getInstance().performRequest(request);
    }
}
