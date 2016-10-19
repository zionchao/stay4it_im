package com.kevin.im.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kevin.im.R;
import com.kevin.im.entities.Conversation;

import java.util.ArrayList;

/**
 * Created by zhangchao_a on 2016/10/14.
 */

public class ConversationAdapter extends BaseAdapter {

    private ArrayList<Conversation> mConversationList;
    private final Context context;
    private ViewHolder mViewHolder;

    public ConversationAdapter(Context context,ArrayList<Conversation> mConversationList)
    {
        this.mConversationList=mConversationList;
        this.context=context;
    }
    @Override
    public int getCount() {
        return mConversationList==null?0:mConversationList.size();
    }

    @Override
    public Object getItem(int position) {
        return mConversationList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null || convertView.getTag() == null) {
            mViewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.activity_conversation_item, null);
            mViewHolder.mConversationAvatarImg = (ImageView) convertView.findViewById(R.id.mConversationAvatarImg);
            mViewHolder.mConversationNumTip = (TextView) convertView.findViewById(R.id.mConversationNumTip);
            mViewHolder.mConversationTimestampLabel = (TextView) convertView.findViewById(R.id.mConversationTimestampLabel);
            mViewHolder.mConversationUsernameLabel = (TextView) convertView.findViewById(R.id.mConversationUsernameLabel);
            mViewHolder.mConversationContentLabel = (TextView) convertView.findViewById(R.id.mConversationContentLabel);
            mViewHolder.mConversationStatusLabel = (TextView) convertView.findViewById(R.id.mConversationStatusLabel);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        Conversation conversation = mConversationList.get(position);
        mViewHolder.mConversationUsernameLabel.setText(conversation.getTargetName());
        mViewHolder.mConversationContentLabel.setText(conversation.getContent());
        return convertView;
    }

    class ViewHolder {
        TextView mConversationStatusLabel;
        TextView mConversationContentLabel;
        TextView mConversationUsernameLabel;
        TextView mConversationTimestampLabel;
        TextView mConversationNumTip;
        ImageView mConversationAvatarImg;
    }

    public void setData(ArrayList<Conversation> conversationList)
    {
        mConversationList=conversationList;
    }

}
