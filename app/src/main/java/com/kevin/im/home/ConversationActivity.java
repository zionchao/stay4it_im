package com.kevin.im.home;

import android.widget.ListView;

import com.kevin.http.AppException;
import com.kevin.http.Request;
import com.kevin.http.RequestManager;
import com.kevin.http.StringCallback;
import com.kevin.im.BaseActivity;
import com.kevin.im.IMApplication;
import com.kevin.im.R;
import com.kevin.im.adapter.ConversationAdapter;
import com.kevin.im.util.Trace;
import com.kevin.im.util.UrlHelper;

/**
 * Created by zhangchao_a on 2016/10/14.
 */

public class ConversationActivity extends BaseActivity {
    private ConversationAdapter mConversationAdapter;
    private ListView mConversationLsv;

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

    private void loadDataFromDB() {

    }

    private void loadDataFromServer() {
        Request request=new Request(UrlHelper.loadConversation());
        request.addHeader("Content-type","application/json");
        request.addHeader("Authorization", IMApplication.getProfile().getAccess_token());
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
