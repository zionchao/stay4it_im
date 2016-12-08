package com.kevin.im.net;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

/**
 * Created by zhangchao_a on 2016/9/19.
 */
public abstract class AbstractCallback<T> implements ICallback<T> {

    private Class<T> entityClass;
    private String path;
    public volatile boolean isCancleHttp;

    @Override
    public T parseResponse(HttpURLConnection connection) throws AppException {
        return parseResponse( connection,null);
    }

    @Override
    public T parseResponse(HttpURLConnection connection,OnProgressUpdateListener listener) throws AppException{
        try {
            checkIsCancle();
            int status =connection.getResponseCode();
            if (status>=200&&status<400)
            {
                if (path==null)
                {
                    ByteArrayOutputStream out=new ByteArrayOutputStream();
                    InputStream is=connection.getInputStream();
                    byte[]buffer=new byte[2048];
                    int len;
                    while ((len=is.read(buffer))!=-1)
                    {
                        checkIsCancle();
                        out.write(buffer,0,len);
                    }
                    is.close();
                    out.flush();
                    out.close();
                    String result= new String(out.toByteArray());
                    T t=onPostRequest(bindData(result));
                    return t;
                }else
                {
                    FileOutputStream out=new FileOutputStream(path);
                    InputStream is=connection.getInputStream();
                    byte[]buffer=new byte[1024];
                    int len;
                    int totalLen=connection.getContentLength();
                    int curLen=0;
                    while ((len=is.read(buffer))!=-1)
                    {
                        checkIsCancle();
                        out.write(buffer,0,len);
                        curLen=curLen+len;
                        if (listener!=null)
                            listener.onProgressUpdate(curLen,totalLen);
                    }
                    is.close();
                    out.flush();
                    out.close();
                    checkIsCancle();
                    T t=onPostRequest(bindData(path));
                    return t;
                }
            }else
                throw new AppException(AppException.ErrorType.SERVER,status,connection.getResponseMessage());
        } catch (Exception e) {
            throw new AppException(AppException.ErrorType.SERVER,e.getMessage());
        }
    }

    @Override
    public T onPostRequest(T t) {
        return t;
    }

    @Override
    public T onPreRequest() {
        return null;
    }

    @Override
    public void onProgressUpdata(int value, int value1) {

    }

    protected abstract T bindData(String result) throws FileNotFoundException, AppException;

    public ICallback setCachePath(String path) {
        this.path=path;
        return this;
    }

    public void checkIsCancle() throws AppException {
        if (isCancleHttp)
            throw new AppException(AppException.ErrorType.CANCLE_HTTP,"取消HTTP请求");
    }

    @Override
    public void cancle() {
        isCancleHttp=true;
    }

//    @Override
//    public void refreshUI() {
//
//    }

    @Override
    public void refreshUI(T t) {

    }

    @Override
    public boolean onCustomOutput(OutputStream out) throws AppException{
        return false;
    }
}
