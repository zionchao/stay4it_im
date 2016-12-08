package com.kevin.im.net;

/**
 * Created by zhangchao_a on 2016/9/19.
 */
public abstract class StringCallback extends AbstractCallback<String> {
    @Override
    protected String bindData(String result) {
        return result;
    }
}
