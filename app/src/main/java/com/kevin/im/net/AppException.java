package com.kevin.im.net;

/**
 * Created by ZhangChao on 2016/9/19.
 */
public class AppException extends Exception{

    public ErrorType type;
    public int status;
    public String responseMessage;

    public enum ErrorType{
        FileNotFoundException, IllegalStateException, ParseException, IOException, CancelException,
        ServerException, ParameterException, TimeoutException, ParseJsonException, JSONException,
        TIMEOUT,SERVER,JSON,IO,FILE_NOT_FOUND,CANCLE_HTTP,UPLOAD,MANUAL
    }

    public AppException(ErrorType type,String message) {
        super(message);
        this.type=type;
    }

    public AppException(ErrorType type,int status, String responseMessage) {
        super(responseMessage);
        this.type=type;
        this.status=status;
        this.responseMessage=responseMessage;
    }
}
