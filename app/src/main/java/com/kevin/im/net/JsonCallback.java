package com.kevin.im.net;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;

/**
 * Created by zhangchao_a on 2016/9/19.
 */
public abstract class JsonCallback<T> extends AbstractCallback<T> {
    @Override
    protected T bindData(String result) {
        Gson gson=new Gson();
        ParameterizedType parameterizedType = (ParameterizedType)this.getClass().getGenericSuperclass();
        //得到泛型的class时候出问题 http://www.iteye.com/problems/1560
//        Class<T> entityClass = (Class<T>) (parameterizedType.getActualTypeArguments()[0]);
        T t=gson.fromJson(result,parameterizedType.getActualTypeArguments()[0]);
        return t;
    }
}
