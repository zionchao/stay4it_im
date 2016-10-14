package com.kevin.im.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.kevin.im.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<String> tagList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,"gAGnnASU9wBTkdygWI0pfenR");
//        tagList.add("111");
//        tagList.add("222");
        PushManager.setTags(this,tagList);

    }
}
