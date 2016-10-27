package com.kevin.im.home;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.kevin.im.util.IDHelper;
import com.kevin.im.util.UrlHelper;
import com.tencent.android.tpush.XGPushManager;

import java.util.ArrayList;

import static com.kevin.im.IMApplication.selfId;
import static com.kevin.im.db.MessageController.queryAllByTimeAsc;
import static com.kevin.im.util.Constants.LOADMORE;
import static com.kevin.im.util.Constants.REFRESH;

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


    private PushWatcher watcher=new PushWatcher(){
        public void messageUpdata(Message message) {
//            super.messageUpdata(message);
//            Conversation conversation = message.copyTo();
//            mChatList.remove(conversation);
//            mChatList.add(0,conversation);
//            mChatAdapter.setData(mChatList);
//            mChatAdapter.notifyDataSetChanged();
        }

        @Override
        public void onMessageReceived(Message message) {
            if (!message.getSenderId().equals(targetId))
            {
                return;
            }
            if (mChatList==null)
                mChatList=new ArrayList<>();
            mChatList.add(message);
            mChatAdapter.setData(mChatList);
            mChatAdapter.notifyDataSetChanged();
        }

        @Override
        public void onMessageUpdated(Message oldMessage, Message newMessage) {
            if (!oldMessage.getReceiverId().equals(targetId))
                return;
            if (mChatList==null)
                mChatList=new ArrayList<>();
            int index=mChatList.indexOf(oldMessage);
            if (index==-1)
                mChatList.add(oldMessage);
            else{
                if (newMessage!=null)
                {
                    mChatList.remove(index);
                    mChatList.add(index,newMessage);
                }else
                {
                    mChatList.remove(index);
                    mChatList.add(index,oldMessage);
                }
            }
            mChatAdapter.setData(mChatList);
            mChatAdapter.notifyDataSetChanged();
        }
    };

    private long endTimeStamp=0;
    private EditText mChatEdt;
    private Button mChatSendBtn;
    private String selfName;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_chat);
//        XGPushManager.registerPush(getApplicationContext());

    }

    @Override
    public void initView() {
        mChatLsv=(ListView)findViewById(R.id.mChatLsv);
        mChatEdt= (EditText) findViewById(R.id.mChatEdt);
        mChatSendBtn= (Button) findViewById(R.id.mChatSendBtn);
        mChatSendBtn.setOnClickListener(this);

        mChatAdapter=new ChatAdapter(this,mChatList);
        mChatLsv.setAdapter( mChatAdapter);
        mChatLoadMoreBtn=(Button)findViewById(R.id.mChatLoadMoreBtn);
        mChatLoadMoreBtn.setOnClickListener(this);
    }

    @Override
    public void initData() {
        targetId=getIntent().getStringExtra(Constants.KEY_TARGETID);
        targetName=getIntent().getStringExtra(Constants.KEY_TARGETNAME);
        selfName=IMApplication.getProfile().getName();
        selfId=IMApplication.selfId;
        loadDataFromDB();
        loadDataFromServer(REFRESH,endTimeStamp);
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
        MessageController.markAsRead(targetId,selfId);

    }

    private void loadDataFromDB() {
         mChatList= queryAllByTimeAsc(targetId,selfId,0);
         mChatAdapter.setData(mChatList);
         mChatAdapter.notifyDataSetChanged();
        if (mChatList!=null&&mChatList.size()>0)
        {
            if (endTimeStamp==0)
                endTimeStamp=MessageController.queryEndTimeStamp(targetId,selfId);
        }
    }

    private void loadDataFromServer(final int status, final long timestamp) {
//        long timestamp=0;

        Request request=new Request(UrlHelper.loadAllMsg(targetId,status,timestamp));
        request.addHeader("Content-type","application/json");
        request.addHeader("Authorization", IMApplication.getToken());
        request.setCallback(new JsonCallback<ArrayList<Message>>() {

            @Override
            public ArrayList<Message> onPreRequest() {
                if (status==LOADMORE)
                {
                    ArrayList<Message> tmp=MessageController.queryAllByTimeAsc(targetId,selfId,timestamp);
                    if (tmp!=null&&tmp.size()>0){
                        return tmp;
                    }
                }
//                mChatList= queryAllByTimeAsc(targetId,selfId,0);
                return super.onPreRequest();
            }

            @Override
            public void refreshUI(ArrayList<Message> messages) {
                mChatList=messages;
                mChatAdapter.setData(mChatList);
                mChatAdapter.notifyDataSetChanged();
            }

//            @Override
//            public void refreshUI() {
////                super.refreshUI();
//                loadDataFromDB();
//            }

            @Override
            public ArrayList<Message> onPostRequest(ArrayList<Message> messages) {
                MessageController.addOrUpdate(messages);
                return MessageController.queryAllByTimeAsc(targetId,selfId,0);
            }

            @Override
            public void onSuccess(ArrayList<Message> messages) {
                if (messages!=null&&messages.size()>0)
                {
                    if (mChatList==null)
                        mChatList=new ArrayList<Message>();
                    if (status==LOADMORE)
                        mChatList.addAll(0,messages);
                    else{
                        mChatList.clear();
                        mChatList.addAll(messages);
                    }
                    notifyDataChanged();
                }else
                {
                    if (status==Constants.LOADMORE)
                        mChatLoadMoreBtn.setEnabled(false);
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
                long timestamp=0;
                if (mChatList!=null&&mChatList.size()>0)
                {
                    timestamp=mChatList.get(0).getTimestamp();
                }
                loadDataFromServer(LOADMORE,timestamp);
                break;
            case R.id.mChatSendBtn:
                if (mChatEdt.getText().toString()!=null)
                {
                    composeMessage(mChatEdt.getText().toString());
                }
                break;
        }
    }

    private void composeMessage(String content) {
        Message message=new Message();
        message.set_id(IDHelper.generateNew());
        message.setContent(content);
        message.setReceiver_name(targetName);
        message.setReceiverId(targetId);
        message.setSender_name(selfName);
        message.setSenderId(selfId);
        message.setContent_type(Message.MessageType.txt);
        message.setTimestamp(System.currentTimeMillis());
        IMPushManager.getInstance(this).sendMessage(message);
    }
}
