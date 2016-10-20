package com.kevin.im.util;

/**
 * Created by zhangchao_a on 2016/10/14.
 */

public class UrlHelper {

    public static final String DEFAULT_ENCODING="UTF-8";
//    public static String DOMAIN="http://show.coreteacher.cn";
    public static String DOMAIN="http://im.stay4it.com";
    private static final String ACTION_LOGIN="/user/account/login";
    private static final String ACTION_BIND_BAIDU_PUSH="/user/account/bindBaiduPushUserId";
    private static final String ACTION_GET_CONVERSATION = "/user/message/getConversationList";
    private static final String ACTION_GET_MESSAGE = "/user/message/getAllMessages";


    public static String getDomain()
    {
        return DOMAIN+"/v1";
    }

    public static String loadLogin()
    {
        return getDomain()+ACTION_LOGIN;
    }

    public static String loadConversation(){
        return getDomain() + ACTION_GET_CONVERSATION;
    }

    public static String loadAllMsg(String id, int state, long timestamp) {
        if (state == Constants.REFRESH && timestamp != 0l) {
            return getDomain() + ACTION_GET_MESSAGE + "/" + id + "?endTimestamp=" + timestamp + "&count=" + Integer.MAX_VALUE;
        }
        return getDomain() + ACTION_GET_MESSAGE + "/" + id + "?timestamp=" + timestamp + "&count=30";
    }

    public static String loadBindBaidu(String userId){
        return getDomain()+ACTION_BIND_BAIDU_PUSH;
    }

}
