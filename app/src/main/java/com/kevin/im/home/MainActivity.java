package com.kevin.im.home;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.kevin.im.R;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;

import java.util.ArrayList;

public class MainActivity extends Activity {
    private ArrayList<String> tagList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 注册接口
        XGPushManager.registerPush(getApplicationContext(),
                new XGIOperateCallback() {
                    @Override
                    public void onSuccess(Object data, int flag) {
                        Log.w("TPush",
                                "+++ register push sucess. token:" + data);

                    }

                    @Override
                    public void onFail(Object data, int errCode, String msg) {
                        Log.w("TPush",
                                "+++ register push fail. token:" + data
                                        + ", errCode:" + errCode + ",msg:"
                                        + msg);


                    }
                });

//        Intent service = new Intent(context, XGPushService.class);
//        context.startService(service);




    }
}
