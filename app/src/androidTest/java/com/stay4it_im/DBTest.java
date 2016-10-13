package com.stay4it_im;

import android.support.test.runner.AndroidJUnit4;

import com.stay4it_im.db.ConversationController;
import com.stay4it_im.db.MessageController;
import com.stay4it_im.entities.Conversation;
import com.stay4it_im.entities.Message;
import com.stay4it_im.util.Trace;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.InstrumentationRegistry.getContext;

/**
 * Created by zhangchao_a on 2016/10/13.
 */

@RunWith(AndroidJUnit4.class)
public class DBTest {

    public static final String SELFID="Stay";
    public static final String TARGETID="Will";

    PushWatcher watcher=new PushWatcher(){
        @Override
        public void messageUpdata(Message message) {
            if (message.getSenderId().equals(SELFID))
            {
                Trace.d("i send a message to "+message.getReceiverId());
            }else
            {
                if (message.getSenderId().equals(TARGETID)){
                    Trace.d("new message send by "+message.getSenderId());
                }else
                {
                    Trace.d("new message send by other");
                }
            }
        }
    };

    PushWatcher controller=new PushWatcher(){
        @Override
        public void messageUpdata(Message message) {
            if (message.getSenderId().equals(TARGETID)){
                Trace.d("new message send by "+message.getSenderId());
            }else
            {
                Trace.d("new message send by other");
            }
        }
    };

    @Before
    public void setUp()
    {
//        PushManager.getInstance(getContext()).addObservers(watcher);
        IMApplication.gContext=getContext();
    }

    @Test
    public void testChat() throws Exception
    {
        // TODO query
        ArrayList<Message> messages= MessageController.queryAllByTimeAsc(SELFID,TARGETID);
        if (messages!=null&&messages.size()>0)
        {
            for (Message message : messages) {
                Trace.d(message.toString());
            }
        }
        //TODO new message
        Message message=Message.test("0001",TARGETID,SELFID);
        PushManager.getInstance(getContext()).handlePush(message);
        //TODO notify
//        PushManager.getInstance(getContext()).handlePush("");
    }

    @Test
    public void testConversation()
    {
        updateConversation();
        Message message=Message.test("0001",TARGETID,SELFID);
        PushManager.getInstance(getContext()).handlePush(message);
    }

    @Test
    public void testBack()
    {
        PushManager.getInstance(getContext()).addObservers(watcher);
        ArrayList<Message> messages= MessageController.queryAllByTimeAsc(SELFID,TARGETID);
        if (messages!=null&&messages.size()>0)
        {
            for (Message message : messages) {
                Trace.d(message.toString());
            }
        }
        Message message=Message.test("0006",SELFID,TARGETID);
        message.setStatus(Message.StatusType.ing);
        PushManager.getInstance(getContext()).handlePush(message);
        PushManager.getInstance(getContext()).removeObservers(watcher);

        PushManager.getInstance(getContext()).addObservers(controller);
        updateConversation();
        message.setStatus(Message.StatusType.done);
        PushManager.getInstance(getContext()).handlePush(message);
        PushManager.getInstance(getContext()).removeObservers(controller);


    }

    private void updateConversation() {

        ArrayList<Conversation> messages = ConversationController.queryAllByTimeDesc();
        if (messages != null && messages.size() > 0) {
            for (Conversation message : messages) {
                Trace.d(message.toString());
            }

        }


    }

    @After
    public void tearDown()
    {
        PushManager.getInstance(getContext()).removeObservers(watcher);
    }
}
