package com.kevin.im.home;

import android.widget.ListView;

import com.baidu.android.pushservice.PushConstants;
import com.baidu.android.pushservice.PushManager;
import com.kevin.http.AppException;
import com.kevin.http.Request;
import com.kevin.http.RequestManager;
import com.kevin.http.StringCallback;
import com.kevin.im.BaseActivity;
import com.kevin.im.IMApplication;
import com.kevin.im.R;
import com.kevin.im.adapter.ConversationAdapter;
import com.kevin.im.db.ConversationController;
import com.kevin.im.entities.Conversation;
import com.kevin.im.entities.Message;
import com.kevin.im.push.IMPushManager;
import com.kevin.im.push.PushWatcher;

import com.kevin.im.util.Trace;
import com.kevin.im.util.UrlHelper;

/**
 * Created by zhangchao_a on 2016/10/14.
 */

public class ConversationActivity extends BaseActivity {
    private ConversationAdapter mConversationAdapter;
    private ListView mConversationLsv;
    private ArrayList<Conversation> mConvsersationList;
    private PushWatcher watcher=new PushWatcher(){
        @Override
        public void messageUpdata(Message message) {
            Conversation conversation=message.copyTo();
            mConvsersationList.remove(conversation);
            mConvsersationList.add(conversation);
            mConversationAdapter.setData(mConvsersationList);
            mConversationAdapter.notifyDataSetChanged();
        }
    };


    private ArrayList<String> tagList=new ArrayList<>();
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_conversation);

    }

    @Override
    public void initView() {
        mConversationLsv=(ListView)findViewById(R.id.mConversationLsv);
    }

    @Override
    public void initData() {
        loadDataFromDB();
        loadDataFromServer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        IMPushManager.getInstance(getApplicationContext()).addObservers(watcher);
//        PushManager.resumeWork(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        IMPushManager.getInstance(getApplicationContext()).removeObservers(watcher);

    }

    @Override
    protected void onStop() {
        super.onStop();
//        PushManager.stopWork(this);
    }

    private void loadDataFromDB() {

    }

    private void loadDataFromServer() {
        Request request=new Request(UrlHelper.loadConversation());
        request.addHeader("content-type","application/json");
        request.addHeader("Authorization", IMApplication.getToken());
        request.setCallback(new StringCallback() {
            @Override
            public void onSuccess(String s) {
                Trace.d(s);
            }

            @Override
            public void onFailuer(AppException e) {
                e.printStackTrace();
            }
        });
        RequestManager.getInstance().performRequest(request);
    }
}
