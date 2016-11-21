package com.kevin.im.home;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import com.kevin.im.BaseActivity;
import com.kevin.im.R;
import com.kevin.im.util.Constants;
import com.kevin.im.util.PrefsAccessor;
import com.kevin.im.util.TextUtil;

/**
 * Created by zhangchao_a on 2016/11/21.
 */

public class WelcomeActivity extends BaseActivity implements View.OnClickListener {

    private static final int ACTION_HOME =0;
    private static final int ACTION_LOGIN = 1;

    private View select_lv;
    private Button select_login_btn;
    private Button select_register_btn;

    private Handler mHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Intent intent=new Intent();
            switch (msg.what){
                case ACTION_LOGIN:
                    intent.setClass(WelcomeActivity.this,LoginActivity.class);
                    break;
                case ACTION_HOME:
                    intent.setClass(WelcomeActivity.this,HomeActivity.class);
                    break;
            }
            startActivity(intent);
        }
    };


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_welcome);
    }

    @Override
    public void initView() {
        select_lv= findViewById(R.id.select_lv);
        select_login_btn=(Button)findViewById(R.id.select_login_btn);
        select_register_btn=(Button)findViewById(R.id.select_register_btn);
        select_login_btn.setOnClickListener(this);
        select_register_btn.setOnClickListener(this);


    }

    @Override
    public void initData() {
        String account= PrefsAccessor.getInstance(this).getString(Constants.KEY_ACCOUNT);
        String pwd=PrefsAccessor.getInstance(this).getString(Constants.KEY_PASSWORD);
        if (TextUtil.isValidate(account,pwd)){
            mHandler.sendEmptyMessageDelayed(ACTION_HOME,2000);
        }else if(TextUtil.isValidate(account)){
            mHandler.sendEmptyMessageDelayed(ACTION_LOGIN,2000);
        }else
        {
            select_lv.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.select_login_btn:
                startActivity(new Intent(this,LoginActivity.class));
                break;
            case R.id.select_register_btn:
                break;
        }
    }
}
