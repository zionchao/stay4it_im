package com.stay4it_im.entities;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;

/**
 * Created by zhangchao_a on 2016/10/12.
 */

@DatabaseTable(tableName = "message")
public class Message implements Serializable {

    public static final String TIMESTAMP="timestamp";
    public static final String SENDERID="senderId";
    public static final String RECEIVERID="receiverId";


    public enum MessageType{
        plain,audio,emo,image,location,txt
    };

    public enum StatusType {
        ing, done, fail
    };

    @DatabaseField(id=true)
    private String id;
    @DatabaseField
    private String senderId;
    @DatabaseField
    private String sender_name;
    private String sender_picture;
    @DatabaseField
    private String receiverId;
    private String receiver_name;
    private String receiver_picture;
    @DatabaseField
    private String content;
    @DatabaseField
    private long timestamp;
    @DatabaseField
    private boolean isRead;
    private int percent;
    @DatabaseField
    private StatusType status;
    @DatabaseField
    private MessageType content_type;
    @DatabaseField(dataType = DataType.SERIALIZABLE)
    private Attachment attachment;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSender_picture() {
        return sender_picture;
    }

    public void setSender_picture(String sender_picture) {
        this.sender_picture = sender_picture;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_picture() {
        return receiver_picture;
    }

    public void setReceiver_picture(String receiver_picture) {
        this.receiver_picture = receiver_picture;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public int getPercent() {
        return percent;
    }

    public void setPercent(int percent) {
        this.percent = percent;
    }

    public MessageType getContent_type() {
        return content_type;
    }

    public void setContent_type(MessageType content_type) {
        this.content_type = content_type;
    }

    public StatusType getStatus() {
        return status;
    }

    public MessageType getType() {
        return content_type;
    }

    public void setType(MessageType type) {
        this.content_type = type;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }

    public Attachment getAttachment() {
        return attachment;
    }

    public void setAttachment(Attachment attachment) {
        this.attachment = attachment;
    }

    public static Message test(String id, String senderId, String receiverId) {
        Message message = new Message();
        message.setId(id);
        message.setContent("content:" + id);
        message.setStatus(StatusType.ing);
        message.setType(MessageType.txt);
        message.setTimestamp(System.currentTimeMillis());
        message.setSenderId(senderId);
        message.setReceiverId(receiverId);
        return message;
    }

    @Override
    public String toString() {
        return senderId + " send "+content+" to " + receiverId +" " + status;
    }

    public Conversation copyTo()
    {
        return null;
    }
}
