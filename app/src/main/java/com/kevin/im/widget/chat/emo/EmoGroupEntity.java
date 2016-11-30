package com.kevin.im.widget.chat.emo;

/**
 * Created by zhangchao_a on 2016/11/10.
 */

public class EmoGroupEntity {

    public int index;
    public int count;
    public int page;
    public String url;
    public String name;

    public EmoGroupEntity(int index, int count, int page, String url, String name) {
        this.index = index;
        this.count = count;
        this.page = page;
        this.url = url;
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

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
}
