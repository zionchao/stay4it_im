package com.kevin.im.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.kevin.im.entities.Message;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangchao_a on 2016/10/13.
 */

public class MessageController {

    public static Dao<Message,String> getDao() throws SQLException{
        return DBController.getInstance().getDB().getDao(Message.class);
    }

    public static void addOrUpdate(Message message)
    {
        try {
            getDao().createOrUpdate(message);
            ConversationController.syncMessage(message);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addOrUpdate(ArrayList<Message> messages) {
        try {
            if (messages != null && messages.size() > 0) {
                for (Message message : messages) {
                    getDao().createOrUpdate(message);
                }
//				ConversationController.syncMessage(t.get(t.size() - 1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void delete(Message message)
    {
        try {
            getDao().delete(message);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Message> queryAllByTimeAsc(String id1,String id2,long timestamp){
        ArrayList<Message> messages=new ArrayList<Message>();
        try {
            QueryBuilder<Message,String>queryBuilder=getDao().queryBuilder ();
            queryBuilder.orderBy(Message.TIMESTAMP,true);
            Where<Message,String>where=queryBuilder.where();
            where.in(Message.SENDERID,id1,id2);
            where.and();
            where.in(Message.RECEIVERID,id1,id2);
            if (timestamp!=0)
            {
                where.and();
                where.lt(Message.TIMESTAMP,timestamp);
            }
            queryBuilder.limit(30l);
            messages= (ArrayList<Message>) getDao().query(queryBuilder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }

    public static Message queryById(String id){
        try {
            return getDao().queryForId(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void markAsRead(String targetId, String selfId) {
        try {
            getDao().updateRaw("UPDATE message SET "+Message.ISREAD +
                    "=? WHRER "+Message.SENDERID+"=? and "+Message.RECEIVERID+"=? order by timestamp desc limit 1","1",targetId,selfId);
            ConversationController.markAsRead(targetId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static long queryEndTimeStamp(String targetId, String selfId) {
        try {
            QueryBuilder<Message,String> queryBuilder=getDao().queryBuilder();
            queryBuilder.orderBy(Message.TIMESTAMP,false);
            Where<Message,String> where=queryBuilder.where();
            where.eq(Message.RECEIVERID,selfId);
            where.and();
            where.eq(Message.SENDERID,targetId);
            queryBuilder.limit(1l);
            List<Message> msg=getDao().query(queryBuilder.prepare());
            if (msg!=null&&msg.size()>0)
                return msg.get(0).getTimestamp();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
