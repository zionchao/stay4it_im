package com.kevin.im;

import android.app.Application;
import android.content.Context;

import com.kevin.im.entities.Profile;

/**
 * Created by zhangchao_a on 2016/10/13.
 */

public class IMApplication extends Application {
    public static Context gContext;
    public static String selfId="Kevin";
    private static Profile profile;

    @Override
    public void onCreate() {
        super.onCreate();
        gContext=this;
//        PushManager.startWork(getApplicationContext(), PushConstants.LOGIN_TYPE_API_KEY,"gAGnnASU9wBTkdygWI0pfenR");

    }

//    public static Profile getProfile() {
//        return profile;
//    }

    public static String getToken()
    {
        if (profile==null)
            return null;
        return profile.getAccess_token();
    }

    public static void setProfile(Profile pProfile) {
        profile = pProfile;
        selfId=pProfile.getUserId();
    }

    public static Profile getProfile() {
        return profile;
    }
}
