package com.kevin.im.push;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.android.pushservice.PushMessageReceiver;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by zhangchao_a on 2016/10/13.
 */

public class PushReceiver extends XGPushBaseReceiver {

    public PushReceiver() {
        super();
    }

    @Override
    public void onRegisterResult(Context context, int i, XGPushRegisterResult xgPushRegisterResult) {

    }

    @Override
    public void onUnregisterResult(Context context, int i) {

    }

    @Override
    public void onSetTagResult(Context context, int i, String s) {

    }

    @Override
    public void onDeleteTagResult(Context context, int i, String s) {

    }

    @Override
    public void onTextMessage(Context context, XGPushTextMessage message) {
        // TODO Auto-generated method stub
        String text = "收到消息:" + message.toString();
        // 获取自定义key-value
        String customContent = message.getCustomContent();
        if (customContent != null && customContent.length() != 0) {
            try {
                JSONObject obj = new JSONObject(customContent);
                // key1为前台配置的key
                if (!obj.isNull("key")) {
                    String value = obj.getString("key");
                    Log.d(LogTag, "get custom value:" + value);
                }
                // ...
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        // APP自主处理消息的过程...
        Log.d(LogTag, text);

    }

    @Override
    public void onNotifactionClickedResult(Context context, XGPushClickedResult xgPushClickedResult) {

    }

    private String LogTag="LogTag";
    @Override
    public void onNotifactionShowedResult(Context context, XGPushShowedResult message) {

    }
}
