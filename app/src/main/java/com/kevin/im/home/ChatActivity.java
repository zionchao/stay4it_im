package com.kevin.im.home;

import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.kevin.http.AppException;
import com.kevin.http.JsonCallback;
import com.kevin.http.Request;
import com.kevin.http.RequestManager;
import com.kevin.im.BaseActivity;
import com.kevin.im.IMApplication;
import com.kevin.im.R;
import com.kevin.im.adapter.ChatAdapter;
import com.kevin.im.db.MessageController;
import com.kevin.im.entities.Message;
import com.kevin.im.push.IMPushManager;
import com.kevin.im.push.PushWatcher;
import com.kevin.im.util.Constants;
import com.kevin.im.util.UrlHelper;
import com.tencent.android.tpush.XGPushManager;

import java.util.ArrayList;

import static com.kevin.im.IMApplication.selfId;

/**
 * Created by zhangchao_a on 2016/10/14.
 */

public class ChatActivity extends BaseActivity implements View.OnClickListener {
    private ChatAdapter mChatAdapter;
    private ListView mChatLsv;
    private ArrayList<Message> mChatList;
    private Button mChatLoadMoreBtn;
    private String targetId;
    private String targetName;
    private static final int REFRESH=0;
    private static final int LOADMORE=1;

    private PushWatcher watcher=new PushWatcher(){
        @Override
        public void messageUpdata(Message message) {
//            super.messageUpdata(message);
//            Conversation conversation = message.copyTo();
//            mChatList.remove(conversation);
//            mChatList.add(0,conversation);
//            mChatAdapter.setData(mChatList);
//            mChatAdapter.notifyDataSetChanged();
        }
    };
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_chat);
//        XGPushManager.registerPush(getApplicationContext());

    }

    @Override
    public void initView() {
        mChatLsv=(ListView)findViewById(R.id.mChatLsv);
        mChatAdapter=new ChatAdapter(this,mChatList);
        mChatLsv.setAdapter( mChatAdapter);
        mChatLoadMoreBtn=(Button)findViewById(R.id.mChatLoadMoreBtn);
        mChatLoadMoreBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        targetId=getIntent().getStringExtra(Constants.KEY_TARGETID);
        targetName=getIntent().getStringExtra(Constants.KEY_TARGETNAME);
        selfId=IMApplication.selfId;
//        loadDataFromDB();
        loadDataFromServer(REFRESH);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IMPushManager.getInstance(getApplicationContext()).addObservers(watcher);

    }

    @Override
    protected void onPause() {
        super.onPause();
        IMPushManager.getInstance(getApplicationContext()).addObservers(watcher);

    }

    private void loadDataFromDB() {
         mChatList= MessageController.queryAllByTimeAsc(targetId,selfId);
         mChatAdapter.setData(mChatList);
         mChatAdapter.notifyDataSetChanged();
    }

    private void loadDataFromServer(final int status) {
        long timestamp=0;
        if (status==LOADMORE&&mChatList!=null&&mChatList.size()>0)
        {
            timestamp=mChatList.get(0).getTimestamp();
        }
        Request request=new Request(UrlHelper.loadAllMsg(targetId,timestamp));
        request.addHeader("Content-type","application/json");
        request.addHeader("Authorization", IMApplication.getToken());
        request.setCallback(new JsonCallback<ArrayList<Message>>() {

            @Override
            public ArrayList<Message> onPreRequest() {
                if (status==LOADMORE)
                {

                }
                mChatList=MessageController.queryAllByTimeAsc(targetId,selfId);
                return super.onPreRequest();
            }

            @Override
            public ArrayList<Message> onPostRequest(ArrayList<Message> messages) {
                MessageController.addOrUpdate(messages);
                return messages;
            }

            @Override
            public void onSuccess(ArrayList<Message> messages) {
                if (messages!=null&&messages.size()>0)
                {
                    if (mChatList==null)
                        mChatList=new ArrayList<Message>();
                    if (status==LOADMORE)
                        mChatList.addAll(0,messages);
                    else
                        mChatList.addAll(messages);
                    notifyDataChanged();
                }
//                loadDataFromDB();
            }

            @Override
            public void onFailuer(AppException error) {
                error.printStackTrace();
            }
        });
        RequestManager.getInstance().performRequest(request);
    }

    private void notifyDataChanged() {
        loadDataFromDB();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        XGPushManager.unregisterPush(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.mChatLoadMoreBtn:
                loadDataFromServer(LOADMORE);
                break;
        }
    }
}
