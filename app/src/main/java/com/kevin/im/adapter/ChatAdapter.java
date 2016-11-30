package com.kevin.im.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kevin.im.R;
import com.kevin.im.entities.Message;
import com.kevin.im.push.IMPushManager;
import com.kevin.im.util.FileUtil;
import com.kevin.im.util.TimeHelper;
import com.kevin.im.widget.chat.emo.EmoParse;

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
        if (mChatList==null)
            return 0;
        return mChatList.size();
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
            if (message.getType()==null||message.getType() == Message.MessageType.txt||message.getType()== Message.MessageType.emo) {
                convertView = createTextOutMsg(position, message);
            } else {
//					convertView = createMediaOutMsg(position, message);
            }
        } else {
            // in
            if (message.getType() == Message.MessageType.txt||message.getType()== Message.MessageType.emo) {
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
//        mChatInMsgLabel.setText(message.getContent());
        if (message.getType()== Message.MessageType.txt){
            mChatInMsgLabel.setText(EmoParse.parseEmo(context,message.getContent()));
        }else if (message.getType()== Message.MessageType.emo){
            String [] emos=message.getContent().split(":");
            String path= FileUtil.getEmoPath(emos[0],emos[1]);
            Bitmap bitmap= BitmapFactory.decodeFile(path);
            mChatInMsgLabel.setBackground(new BitmapDrawable(bitmap));
        }
        return convertView;
    }

    private View createTextOutMsg(int position, final Message message) {
        View convertView = LayoutInflater.from(context).inflate(R.layout.activity_chat_text_out_item, null);
        ImageView mChatOutAvatarImg = (ImageView) convertView.findViewById(R.id.mChatOutAvatarImg);
        TextView mChatOutMsgLabel = (TextView) convertView.findViewById(R.id.mChatOutMsgLabel);
        Button mChatOutMsgResendBtn= (Button) convertView.findViewById(R.id.mChatOutMsgResendBtn);
        ProgressBar mChatOutMsgStatus= (ProgressBar) convertView.findViewById(R.id.mChatOutMsgStatus);
        mChatOutMsgStatus.setVisibility(View.GONE);
        if (message.getStatus()== Message.StatusType.ing)
        {
            mChatOutMsgStatus.setVisibility(View.VISIBLE);
        }else if (message.getStatus()== Message.StatusType.fail)
        {
            mChatOutMsgResendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IMPushManager.getInstance(context).sendMessage(message);
                }
            });
        }
        TextView mChatTimeLabel = (TextView) convertView.findViewById(R.id.mChatTimeLabel);
        mChatTimeLabel.setText(TimeHelper.getTimeRule3(message.getTimestamp()));
//        mChatOutMsgLabel.setText(message.getContent());
        if (message.getType()== Message.MessageType.txt){
            mChatOutMsgLabel.setText(EmoParse.parseEmo(context,message.getContent()));
        }else if (message.getType()== Message.MessageType.emo){
            String[] emos=message.getContent().split(":");
            String path=FileUtil.getEmoPath(emos[0],emos[1]);
            Bitmap bitmap=BitmapFactory.decodeFile(path);
            mChatOutMsgLabel.setText(null);
            mChatOutMsgLabel.setBackground(new BitmapDrawable(bitmap));
        }
        return convertView;
    }

    public void setData(ArrayList<Message> chatList)
    {
        mChatList=chatList;
    }

}
