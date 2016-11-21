package com.kevin.im.home;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.kevin.http.AppException;
import com.kevin.http.JsonCallback;
import com.kevin.http.Request;
import com.kevin.http.RequestManager;
import com.kevin.im.BaseActivity;
import com.kevin.im.IMApplication;
import com.kevin.im.R;
import com.kevin.im.entities.Profile;
import com.kevin.im.util.Constants;
import com.kevin.im.util.PrefsAccessor;
import com.kevin.im.util.UrlHelper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhangchao_a on 2016/10/13.
 */

public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText mLoginAccountEdt;
    private EditText mLoginPwdEdt;
    private Button mLoginSubmitBtn;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initView() {
        mLoginAccountEdt= (EditText)findViewById(R.id.mLoginAccountEdt);
        mLoginPwdEdt= (EditText)findViewById(R.id.mLoginPwdEdt);
        mLoginSubmitBtn= (Button)findViewById(R.id.mLoginSubmitBtn);
    }

    @Override
    public void initData() {
        mLoginSubmitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.mLoginSubmitBtn:
                String account=mLoginAccountEdt.getText().toString();
                String pwd=mLoginPwdEdt.getText().toString();
                doLogin(account,pwd);
                break;
        }
    }


    private void doLogin(final String account, final String pwd) {
        Request request =new Request(UrlHelper.loadLogin(), Request.RequestMethod.POST);
        JSONObject json = new JSONObject();
        try {
            json.put("account", account);
            json.put("password", pwd);
            json.put("clientId", "android");
            json.put("clientVersion", "1.0.0");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.addHeader("content-type", "application/json");
        request.content=json.toString();
        request.setCallback(new JsonCallback<Profile>(){
            @Override
            public void onSuccess(Profile profile) {
//                Trace.d(profile.toString());
                IMApplication.setProfile(profile);
                PrefsAccessor.getInstance(LoginActivity.this).saveString(Constants.KEY_ACCOUNT,account);
                PrefsAccessor.getInstance(LoginActivity.this).saveString(Constants.KEY_PASSWORD,pwd);
                Intent intent=new Intent(LoginActivity.this,ConversationActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailuer(AppException e) {
               e.printStackTrace();
            }
        });
        RequestManager.getInstance().performRequest(request);
    }
}
