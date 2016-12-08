package com.kevin.im.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by ZhangChao on 2016/9/21.
 */

public class RequestManager {

    private HashMap<String,ArrayList<Request>> cacheSet;
    private static RequestManager manager;
    private RequestManager()
    {
        cacheSet=new HashMap<>();
    }

    private Executor executor= Executors.newFixedThreadPool(5);

    public static RequestManager getInstance() {
        if (manager==null)
        {
            manager=new RequestManager();
        }
        return manager;
    }

    public void performRequest(Request request)
    {
        request.execute(executor);
        if (!cacheSet.containsKey(request.tag))
        {
            ArrayList<Request> reqList=new ArrayList<>();
            reqList.add(request);
            cacheSet.put(request.tag,reqList);
        }else
          cacheSet.get(request.tag).add(request);
    }

    public void cancleRequest(String tag)
    {
        if(tag==null||tag.equals(""))
            return;
        if (cacheSet.containsKey(tag))
        {
            ArrayList<Request> reqList=cacheSet.remove(tag);
            for (Request request:reqList) {
                request.cancle(true);
            }
        }

    }

    public void cancleAll()
    {
       for (Map.Entry<String,ArrayList<Request>> map:cacheSet.entrySet()){
           ArrayList<Request> reqList=cacheSet.get(map.getKey());
               for (Request req:reqList)
                   if (!req.isCancleHttp)
                        req.cancle(true);
       }
    }
}
