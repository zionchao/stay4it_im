package com.stay4it_im.entities;

import java.io.Serializable;

/**
 * Created by zhangchao_a on 2016/10/13.
 */

public class Attachment implements Serializable{
    private String url;
    private String name;
    private String desc;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
