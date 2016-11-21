package com.kevin.im;

import android.support.test.runner.AndroidJUnit4;

import com.kevin.im.push.IMPushManager;
import com.kevin.im.push.PushWatcher;
import com.kevin.im.db.ConversationController;
import com.kevin.im.db.MessageController;
import com.kevin.im.entities.Conversation;
import com.kevin.im.entities.Message;
import com.kevin.im.util.Trace;

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
//        public void messageUpdata(Message message) {
//            if (message.getSenderId().equals(SELFID))
//            {
//                Trace.d("i send a message to "+message.getReceiverId());
//            }else
//            {
//                if (message.getSenderId().equals(TARGETID)){
//                    Trace.d("new message send by "+message.getSenderId());
//                }else
//                {
//                    Trace.d("new message send by other");
//                }
//            }
//        }
    };

    PushWatcher controller=new PushWatcher(){
//        @Override
//        public void messageUpdata(Message message) {
//            if (message.getSenderId().equals(TARGETID)){
//                Trace.d("new message send by "+message.getSenderId());
//            }else
//            {
//                Trace.d("new message send by other");
//            }
//        }
    };

    @Before
    public void setUp()
    {
//        IMPushManager.getInstance(getContext()).addObservers(watcher);
        IMApplication.gContext=getContext();
    }

    @Test
    public void testChat() throws Exception
    {
        // TODO query
        ArrayList<Message> messages= MessageController.queryAllByTimeAsc(SELFID,TARGETID,0);
        if (messages!=null&&messages.size()>0)
        {
            for (Message message : messages) {
                Trace.d(message.toString());
            }
        }
        //TODO new message
        Message message=Message.test("0001",TARGETID,SELFID);
        IMPushManager.getInstance(getContext()).handlePush(message);
        //TODO notify
//        IMPushManager.getInstance(getContext()).handlePush("");
    }

    @Test
    public void testConversation()
    {
        updateConversation();
        Message message=Message.test("0001",TARGETID,SELFID);
        IMPushManager.getInstance(getContext()).handlePush(message);
    }

    @Test
    public void testBack()
    {
        IMPushManager.getInstance(getContext()).addObservers(watcher);
        ArrayList<Message> messages= MessageController.queryAllByTimeAsc(SELFID,TARGETID,0);
        if (messages!=null&&messages.size()>0)
        {
            for (Message message : messages) {
                Trace.d(message.toString());
            }
        }
        Message message=Message.test("0006",SELFID,TARGETID);
        message.setStatus(Message.StatusType.ing);
        IMPushManager.getInstance(getContext()).handlePush(message);
        IMPushManager.getInstance(getContext()).removeObservers(watcher);

        IMPushManager.getInstance(getContext()).addObservers(controller);
        updateConversation();
        message.setStatus(Message.StatusType.done);
        IMPushManager.getInstance(getContext()).handlePush(message);
        IMPushManager.getInstance(getContext()).removeObservers(controller);


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
        IMPushManager.getInstance(getContext()).removeObservers(watcher);
    }
}
