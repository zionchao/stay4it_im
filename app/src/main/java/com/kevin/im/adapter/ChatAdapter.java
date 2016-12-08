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

import com.kevin.im.IMApplication;
import com.kevin.im.R;
import com.kevin.im.entities.Message;
import com.kevin.im.push.IMPushManager;
import com.kevin.im.util.FileUtil;
import com.kevin.im.util.TimeHelper;
import com.kevin.im.widget.chat.emo.EmoParse;

import java.util.ArrayList;

import static com.kevin.im.entities.Message.StatusType.fail;

/**
 * Created by zhangchao_a on 2016/10/14.
 */

public class ChatAdapter extends BaseAdapter {

    private ArrayList<Message> mChatList;
    private final Context context;
    private static final int MESSAGE_VIWE_TXT_TO=0;
    private static final int MESSAGE_VIWE_TXT_FROM=1;
    private static final int MESSAGE_VIWE_IMG_TO=2;
    private static final int MESSAGE_VIWE_IMG_FROM=3;

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
    public int getItemViewType(int position) {
        Message message=mChatList.get(position);
        boolean isSelf=message.getSenderId().equals(IMApplication.selfId);
        if (message.getType()==null){
            return MESSAGE_VIWE_TXT_TO;
        }
        switch(message.getType()){
            case txt:
                return isSelf?MESSAGE_VIWE_TXT_TO:MESSAGE_VIWE_TXT_FROM;
            case emo:
                return isSelf?MESSAGE_VIWE_IMG_TO:MESSAGE_VIWE_IMG_FROM;
            default:
                break;
        }
        return -1;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
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
        int type=getItemViewType(position);
        if (convertView==null||convertView.getTag()==null){
            switch(type){
                case MESSAGE_VIWE_TXT_TO:
                    convertView=LayoutInflater.from(context).inflate(R.layout.activity_chat_text_out_item,null);
                    holder=new TxtToHolder();
                    break;
                case MESSAGE_VIWE_TXT_FROM:
                    convertView=LayoutInflater.from(context).inflate(R.layout.activity_chat_text_in_item,null);
                    holder=new TxtFromHolder();
                    break;
                case MESSAGE_VIWE_IMG_TO:
                    convertView=LayoutInflater.from(context).inflate(R.layout.activity_chat_img_out_item,null);
                    holder=new ImgToHolder();
                    break;
                case MESSAGE_VIWE_IMG_FROM:
                    convertView=LayoutInflater.from(context).inflate(R.layout.activity_chat_img_in_item,null);
                    holder=new ImgFromHolder();
                    break;
                default:
                    break;
            }
            holder.initializeView(convertView);
            convertView.setTag(holder);
        }else{
            switch(type){
                case MESSAGE_VIWE_TXT_TO:
                    holder=(TxtToHolder)convertView.getTag();
                    break;
                case MESSAGE_VIWE_TXT_FROM:
                    holder=(TxtFromHolder)convertView.getTag();
                    break;
                case MESSAGE_VIWE_IMG_TO:
                    holder=(ImgToHolder)convertView.getTag();
                    break;
                case MESSAGE_VIWE_IMG_FROM:
                    holder=(ImgFromHolder)convertView.getTag();
                    break;
                default:
                    break;
            }
        }
        holder.initializeData(message);
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
        }else if (message.getStatus()== fail)
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



    private ViewHolder holder;

    abstract class ViewHolder{
        public abstract void initializeView(View convertView);
        public abstract void initializeData(Message message);
    }

    private class TxtToHolder extends ViewHolder{

        private ImageView mChatOutAvatarImg;
        private TextView mChatOutMsgLabel;
        private Button mChatOutMsgResendBtn;
        private ProgressBar mChatOutMsgStatus;
        private TextView mChatTimeLabel;

        @Override
        public void initializeView(View convertView) {
            mChatOutAvatarImg = (ImageView) convertView.findViewById(R.id.mChatOutAvatarImg);
            mChatOutMsgLabel = (TextView) convertView.findViewById(R.id.mChatOutMsgLabel);
            mChatOutMsgResendBtn = (Button) convertView.findViewById(R.id.mChatOutMsgResendBtn);
            mChatOutMsgStatus = (ProgressBar) convertView.findViewById(R.id.mChatOutMsgStatus);
            mChatTimeLabel = (TextView) convertView.findViewById(R.id.mChatTimeLabel);
        }

        @Override
        public void initializeData(final Message message) {
            mChatOutMsgStatus.setVisibility(View.GONE);
            if (message.getStatus() == Message.StatusType.ing) {
                mChatOutMsgStatus.setVisibility(View.VISIBLE);
            } else if (message.getStatus() == Message.StatusType.fail) {
                mChatOutMsgResendBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        IMPushManager.getInstance(context).sendMessage(message);
                    }
                });
            }

            mChatTimeLabel.setText(TimeHelper.getTimeRule3(message.getTimestamp()));
            if (message.getType() == Message.MessageType.txt) {
                mChatOutMsgLabel.setText(EmoParse.parseEmo(context, message.getContent()));
            } else {
                if (!message.getContent().contains(":"))
                {
                    mChatOutMsgLabel.setText(EmoParse.parseEmo(context, message.getContent()));
                    return;
                }
                String[] emos = message.getContent().split(":");
                String path = FileUtil.getEmoPath(emos[0], emos[1]);
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                mChatOutMsgLabel.setText(null);
                mChatOutMsgLabel.setBackground(new BitmapDrawable(bitmap));
            }
        }
    }

    private class TxtFromHolder extends ViewHolder{
        private TextView mChatTimeLabel;
        private TextView mChatInMsgLabel;
        private ImageView mChatInAvatarImg;

        @Override
        public void initializeView(View convertView) {
            mChatInAvatarImg = (ImageView) convertView.findViewById(R.id.mChatInAvatarImg);
            mChatInMsgLabel = (TextView) convertView.findViewById(R.id.mChatInMsgLabel);
            mChatTimeLabel = (TextView) convertView.findViewById(R.id.mChatTimeLabel);
        }

        @Override
        public void initializeData(Message message) {
            mChatTimeLabel.setText(TimeHelper.getTimeRule3(message.getTimestamp()));
            if (message.getType() == Message.MessageType.txt) {
                mChatInMsgLabel.setText(EmoParse.parseEmo(context, message.getContent()));
            } else {
                String[] emos = message.getContent().split(":");
                String path = FileUtil.getEmoPath(emos[0], emos[1]);
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                mChatInMsgLabel.setBackground(new BitmapDrawable(bitmap));
            }
        }
    }

    private class ImgToHolder extends ViewHolder{
        private ImageView mChatOutAvatarImg;
        private ImageView mChatOutMsgImg;
        private View mChatOutMsgStatus;
        private TextView mChatTimeLabel;
        private Button mChatOutMsgResendBtn;

        @Override
        public void initializeView(View convertView) {
            mChatOutAvatarImg = (ImageView) convertView.findViewById(R.id.mChatOutAvatarImg);
            mChatOutMsgImg = (ImageView) convertView.findViewById(R.id.mChatOutMsgImg);
            mChatOutMsgStatus = convertView.findViewById(R.id.mChatOutMsgStatus);
            mChatTimeLabel = (TextView) convertView.findViewById(R.id.mChatTimeLabel);
            mChatOutMsgResendBtn = (Button) convertView.findViewById(R.id.mChatOutMsgResendBtn);
        }

        @Override
        public void initializeData(final Message message) {
            mChatTimeLabel.setVisibility(View.VISIBLE);
            mChatTimeLabel.setText(TimeHelper.getTimeRule3(message.getTimestamp()));
            mChatOutMsgStatus.setVisibility(View.GONE);
            if (message.getStatus() == Message.StatusType.ing) {
                mChatOutMsgStatus.setVisibility(View.VISIBLE);
            } else if (message.getStatus() == Message.StatusType.fail) {
                mChatOutMsgResendBtn.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        IMPushManager.getInstance(context).sendMessage(message);
                    }
                });
            }
            String[] emos = message.getContent().split(":");
            String path = FileUtil.getEmoPath(emos[0], emos[1]);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            mChatOutMsgImg.setImageBitmap(bitmap);
//				AttachmentEntity attachment = message.getAttachments().get(0);
//				if (TextUtil.isValidate(attachment.getFile_path())) {
//					File file = new File(attachment.getFile_path());
//					if (file.exists()) {
//						Utils.showImage(attachment.getFile_path(), mChatOutMsgImg,false,true);
//					}
//				}else {
//					Utils.showImage(attachment.getFile_url(), mChatOutMsgImg,false,true);
//				}
//				Utils.showImage(mCurrentPicture, mChatOutAvatarImg);
        }
    }

    private class ImgFromHolder extends ViewHolder{
        private ImageView mChatInAvatarImg;
        private ImageView mChatInMsgImg;
        private TextView mChatTimeLabel;

        @Override
        public void initializeView(View convertView) {
            mChatInAvatarImg = (ImageView) convertView.findViewById(R.id.mChatInAvatarImg);
            mChatInMsgImg = (ImageView) convertView.findViewById(R.id.mChatInMsgImg);
            mChatTimeLabel = (TextView) convertView.findViewById(R.id.mChatTimeLabel);
        }

        @Override
        public void initializeData(Message message) {
            mChatTimeLabel.setVisibility(View.VISIBLE);
            mChatTimeLabel.setText(TimeHelper.getTimeRule3(message.getTimestamp()));
//				Utils.showImage(message.getAttachments().get(0).getFile_url(), mChatInMsgImg,false,true);
//				Utils.showImage(mTargetPicture, mChatInAvatarImg);
            String[] emos = message.getContent().split(":");
            String path = FileUtil.getEmoPath(emos[0], emos[1]);
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            mChatInMsgImg.setImageBitmap(bitmap);
        }
    }
}
