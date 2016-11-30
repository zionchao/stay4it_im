package com.kevin.im;

import android.app.Application;
import android.content.Context;

import com.google.gson.Gson;
import com.kevin.im.entities.Profile;
import com.kevin.im.util.Constants;
import com.kevin.im.util.PrefsAccessor;

/**
 * Created by zhangchao_a on 2016/10/13.
 */

public class IMApplication extends Application {
    public static Context gContext;
    public static String selfId="Kevin";
    private static Profile profile;
    public static int mAppState=-1;

    @Override
    public void onCreate() {
        super.onCreate();
        gContext=this;
        mAppState=-1;
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
        Gson gson=new Gson();
        PrefsAccessor.getInstance(gContext).saveString(Constants.KEY_PROFILE,gson.toJson(profile));
    }

    public static Profile getProfile() {
        return profile;
    }

    public static void initProfile() {
        String json= PrefsAccessor.getInstance(gContext).getString(Constants.KEY_PROFILE);
        Gson gson=new Gson();
        Profile profile=gson.fromJson(json,Profile.class);
        IMApplication.profile=profile;
        selfId=profile.getUserId();
    }
}
