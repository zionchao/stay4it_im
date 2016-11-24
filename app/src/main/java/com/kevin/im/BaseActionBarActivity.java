package com.kevin.im;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.kevin.im.home.HomeActivity;


/**
 * Created by zhangchao_a on 2016/11/23.
 */

public abstract class BaseActionBarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
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



    protected abstract void setContentView();
    protected abstract void initView();
    protected abstract void initData();

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void protectApp() {
        Intent intent=new Intent(this,HomeActivity.class);
//        intent.putExtra(Constants.KEY_PROTECT_APP,true);
        startActivity(intent);
        finish();
    }

}
