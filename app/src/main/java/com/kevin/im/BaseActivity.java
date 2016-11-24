package com.kevin.im;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.kevin.im.home.HomeActivity;
import com.kevin.im.util.Constants;

/**
 * Created by zhangchao_a on 2016/10/13.
 */

public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(IMApplication.mAppState!=-1){
            setContentView();
            initView();
            initData();
        }else
        {
            protectApp();
        }
    }

    protected void protectApp() {
        Intent intent=new Intent(this,HomeActivity.class);
        intent.putExtra(Constants.KEY_PROTECT_APP,true);
        startActivity(intent);
        finish();
    }


    public abstract  void setContentView() ;

    public abstract void initView() ;

    public abstract void initData();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
