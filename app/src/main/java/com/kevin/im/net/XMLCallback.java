package com.kevin.im.net;

/**
 * Created by zhangchao_a on 2016/9/19.
 */
public abstract class XMLCallback<T> extends AbstractCallback<T> {
    @Override
    protected T bindData(String result) {
        return null;
    }
}
