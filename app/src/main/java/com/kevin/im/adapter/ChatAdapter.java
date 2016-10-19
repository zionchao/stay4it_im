package com.kevin.im.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kevin.im.R;
import com.kevin.im.entities.Message;
import com.kevin.im.util.TimeHelper;

import java.util.ArrayList;

import static com.kevin.im.IMApplication.selfId;

/**
 * Created by zhangchao_a on 2016/10/14.
 */

public class ChatAdapter extends BaseAdapter {

    private ArrayList<Message> mChatList;
    private final Context context;

    public ChatAdapter(Context context, ArrayList<Message> mConversationList)
    {
        this.mChatList=mConversationList;
        this.context=context;
    }

    @Override
    public int getCount() {
        return mChatList==null?0:mChatList.size();
    }

    @Override
    public Object getItem(int position) {
        return mChatList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Message message = mChatList.get(position);
        if (selfId.equalsIgnoreCase(message.getSenderId())) {
            // out
            if (message.getType() == Message.MessageType.txt) {
                convertView = createTextOutMsg(position, message);
            } else {
//					convertView = createMediaOutMsg(position, message);
            }
        } else {
            // in
            if (message.getType() == Message.MessageType.txt) {
                convertView = createTextInMsg(position, message);
            } else {
//					convertView = createMediaInMsg(position, message);
            }
        }
        return convertView;
    }

    private View createTextInMsg(int position, Message message) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.activity_chat_text_in_item, null);
        ImageView mChatInAvatarImg = (ImageView) convertView.findViewById(R.id.mChatInAvatarImg);
        TextView mChatInMsgLabel = (TextView) convertView.findViewById(R.id.mChatInMsgLabel);
        TextView mChatTimeLabel = (TextView) convertView.findViewById(R.id.mChatTimeLabel);
        mChatTimeLabel.setText(TimeHelper.getTimeRule3(message.getTimestamp()));
        mChatInMsgLabel.setText(message.getContent());
        return convertView;
    }

    private View createTextOutMsg(int position, Message message) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.activity_chat_text_out_item, null);
        ImageView mChatOutAvatarImg = (ImageView) convertView.findViewById(R.id.mChatOutAvatarImg);
        TextView mChatOutMsgLabel = (TextView) convertView.findViewById(R.id.mChatOutMsgLabel);
        TextView mChatTimeLabel = (TextView) convertView.findViewById(R.id.mChatTimeLabel);
        mChatTimeLabel.setText(TimeHelper.getTimeRule3(message.getTimestamp()));
        mChatOutMsgLabel.setText(message.getContent());
        return convertView;
    }

    public void setData(ArrayList<Message> chatList)
    {
        mChatList=chatList;
    }

}
