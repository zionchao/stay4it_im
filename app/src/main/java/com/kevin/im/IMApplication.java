package com.kevin.im;

import android.app.Application;
import android.content.Context;

/**
 * Created by zhangchao_a on 2016/10/13.
 */

public class IMApplication extends Application {
    public static Context gContext;
    public static String selfId="Stay";

    @Override
    public void onCreate() {
        super.onCreate();
        gContext=this;
    }
}
