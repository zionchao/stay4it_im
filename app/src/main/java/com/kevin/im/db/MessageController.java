package com.kevin.im.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;
import com.kevin.im.entities.Message;

import java.sql.SQLException;
import java.util.ArrayList;

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

    public static void delete(Message message)
    {
        try {
            getDao().delete(message);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Message> queryAllByTimeAsc(String id1,String id2){
        ArrayList<Message> messages=new ArrayList<Message>();
        try {
            QueryBuilder<Message,String>queryBuilder=getDao().queryBuilder ();
            queryBuilder.orderBy(Message.TIMESTAMP,true);
            Where<Message,String>where=queryBuilder.where();
            where.in(Message.SENDERID,id1,id2);
            where.and();
            where.in(Message.RECEIVERID,id1,id2);
            messages= (ArrayList<Message>) getDao().query(queryBuilder.prepare());
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return messages;
    }
}
