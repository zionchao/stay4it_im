package com.kevin.im.entities;

import com.kevin.im.BaseFragment;

/**
 * Created by zhangchao_a on 2016/11/23.
 */

public class Tab {
    private int iconResId;
    private int infoResId;
    private Class<? extends BaseFragment> fragment;

    public Tab(int iconResId, int infoResId, Class<? extends BaseFragment> fragment) {
        this.iconResId = iconResId;
        this.infoResId = infoResId;
        this.fragment = fragment;
    }

    public int getIconResId() {
        return iconResId;
    }

    public void setIconResId(int iconResId) {
        this.iconResId = iconResId;
    }

    public int getInfoResId() {
        return infoResId;
    }

    public void setInfoResId(int infoResId) {
        this.infoResId = infoResId;
    }

    public Class<? extends BaseFragment> getFragment() {
        return fragment;
    }

    public void setFragment(Class<? extends BaseFragment> fragment) {
        this.fragment = fragment;
    }
}
