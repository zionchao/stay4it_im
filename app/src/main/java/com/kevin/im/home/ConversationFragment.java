package com.kevin.im.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kevin.im.BaseFragment;
import com.kevin.im.IMApplication;
import com.kevin.im.R;
import com.kevin.im.adapter.ConversationAdapter;
import com.kevin.im.db.ConversationController;
import com.kevin.im.entities.Conversation;
import com.kevin.im.entities.Message;
import com.kevin.im.net.AppException;
import com.kevin.im.net.JsonCallback;
import com.kevin.im.net.Request;
import com.kevin.im.net.RequestManager;
import com.kevin.im.push.IMPushManager;
import com.kevin.im.push.PushWatcher;
import com.kevin.im.util.Constants;
import com.kevin.im.util.UrlHelper;

import java.util.ArrayList;

/**
 * Created by zhangchao_a on 2016/11/23.
 */

public class ConversationFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private ConversationAdapter mConversationAdapter;
    private ListView mConversationLsv;
    private ArrayList<Conversation> mConversationList;

    private PushWatcher watcher=new PushWatcher(){
        @Override
        public void onMessageReceived(Message message) {
            Conversation conversation = message.copyTo();
            mConversationList.remove(conversation);
            mConversationList.add(0,conversation);
            mConversationAdapter.setData(mConversationList);
            mConversationAdapter.notifyDataSetChanged();
        }

        @Override
        public void onMessageUpdated(Message oldMessage, Message newMessage) {
            Conversation conversation=oldMessage.copyTo();
            mConversationList.remove(conversation);
            if (newMessage!=null)
                mConversationList.add(0,newMessage.copyTo());
            else
                mConversationList.add(conversation);
            mConversationAdapter.setData(mConversationList);
            mConversationAdapter.notifyDataSetChanged();
        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_conversation,null);
        mConversationLsv=(ListView)view.findViewById(R.id.mConversationLsv);
        mConversationLsv.setOnItemClickListener(this);
        IMPushManager.getInstance(getActivity().getApplicationContext()).addObservers(watcher);
        mConversationAdapter=new ConversationAdapter(getActivity(),mConversationList);
        mConversationLsv.setAdapter(mConversationAdapter);
        loadDataFromDB();
        loadDataFromServer();
        return view;
    }

    private void loadDataFromDB() {
        mConversationList= ConversationController.queryAllByTimeDesc();
        mConversationAdapter.setData(mConversationList);
        mConversationAdapter.notifyDataSetChanged();
    }

    private void loadDataFromServer() {
        Request request=new Request(UrlHelper.loadConversation());
        request.addHeader("Content-type","application/json");
        request.addHeader("Authorization", IMApplication.getToken());
        request.setCallback(new JsonCallback<ArrayList<Message>>() {

            @Override
            public ArrayList<Message> onPostRequest(ArrayList<Message> messages) {
                for (Message message:messages) {
                    ConversationController.syncMessage(message);
//                    Trace.d(message.toString());
                }
                return messages;
            }

            @Override
            public void onSuccess(ArrayList<Message> messages) {
                if (messages!=null&&messages.size()>0) {
                    notifyDataChanged();
                }
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(getActivity(),ChatActivity.class);
        Conversation conversation=mConversationList.get(position);
        intent.putExtra(Constants.KEY_TARGETID,conversation.getTargetId());
        intent.putExtra(Constants.KEY_TARGETNAME,conversation.getTargetName());
        startActivity(intent);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        IMPushManager.getInstance(getActivity().getApplicationContext()).removeObservers(watcher);
    }
}
