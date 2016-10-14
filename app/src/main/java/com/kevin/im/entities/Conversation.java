package com.kevin.im.entities;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by zhangchao_a on 2016/10/13.
 */

@DatabaseTable(tableName = "conversation")
public class Conversation {
    public static final String TIMESTAMP="timestamp";
    public static final String UNREADNUM="unreadNum";
    public static final String TARGETID="targetId";
    @DatabaseField(id = true)
    private String targetId;
    @DatabaseField
    private String messageId;
    @DatabaseField
    private String content;
    @DatabaseField
    private String targetName;
    @DatabaseField
    private String targetPicture;
    @DatabaseField
    private Message.MessageType type;
    @DatabaseField
    private int unreadNum;
    @DatabaseField
    private Message.StatusType status;
    @DatabaseField
    private long timestamp;

    public String getTargetId() {
        return targetId;
    }

    public void setTargetId(String targetId) {
        this.targetId = targetId;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Message.MessageType getType() {
        return type;
    }

    public void setType(Message.MessageType type) {
        this.type = type;
    }

    public int getUnreadNum() {
        return unreadNum;
    }

    public void setUnreadNum(int unreadNum) {
        this.unreadNum = unreadNum;
    }

    public Message.StatusType getStatus() {
        return status;
    }

    public void setStatus(Message.StatusType status) {
        this.status = status;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetPicture() {
        return targetPicture;
    }

    public void setTargetPicture(String targetPicture) {
        this.targetPicture = targetPicture;
    }

    @Override
    public String toString() {
        return targetId + " msg " + content +" unread "+ unreadNum + " status: " + status;
    }

    @Override
    public int hashCode() {
        return targetId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return hashCode()==obj.hashCode();
    }
}
