package com.kevin.im.net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executor;

/**
 * Created by zhangchao_a on 2016/9/18.
 */
public class Request {

    public ICallback iCallback;
    public boolean enableProgressUpdate;
    public OnGloableExceptionListener onGloableExceptionListener;
    public int maxRetryTime=3;
    public boolean isCancleHttp;
    public String tag;
    public RequestTask task;
    public String filePath;
    public ArrayList<FileEntity> fileEntities;


    public enum RequestMethod{
        GET,POST,PUT,DELETE
    }
    public  String url;
    public Map<String,String> headers;
    public  String content;
    public  RequestMethod method;


    public  Request(String url)
    {
        this.url=url;
        this.method= RequestMethod.GET;
        headers=new HashMap<>();
    }

    public  Request(String url,RequestMethod method)
    {
        this.url=url;
        this.method=method;
        headers=new HashMap<>();
    }

    public void enableProgressUpdate(boolean enable) {
        this.enableProgressUpdate=enable;
    }

    public void setOnGloableListener(OnGloableExceptionListener onGloableExptionListener) {
        this.onGloableExceptionListener=onGloableExptionListener;
    }

    public void checkIsCancle() throws AppException {
        if (isCancleHttp)
            throw new AppException(AppException.ErrorType.CANCLE_HTTP,"取消HTTP请求");
    }

    public void execute()
    {
        task=new RequestTask(this);
        task.execute();
    }

    public void execute(Executor executor){
        task=new RequestTask(this);
        task.executeOnExecutor(executor);
    }

    public void cancle(boolean force)
    {
        this.isCancleHttp=true;
        this.iCallback.cancle();
        if (force)
        {
            task.cancel(force);
        }
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public void setCallback(ICallback iCallback)
    {
        this.iCallback=iCallback;
    }

    public void addHeader(String key,String value)
    {
        headers.put(key,value);
    }

}
