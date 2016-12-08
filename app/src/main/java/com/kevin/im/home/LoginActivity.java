package com.kevin.im.home;

import android.content.Intent;
import android.view.View;
import android.widget.Button;

import com.kevin.im.BaseActionBarActivity;
import com.kevin.im.IMApplication;
import com.kevin.im.R;
import com.kevin.im.entities.Profile;
import com.kevin.im.net.AppException;
import com.kevin.im.net.JsonCallback;
import com.kevin.im.net.Request;
import com.kevin.im.net.RequestManager;
import com.kevin.im.util.Constants;
import com.kevin.im.util.PrefsAccessor;
import com.kevin.im.util.TextUtil;
import com.kevin.im.util.UrlHelper;
import com.kevin.im.widget.EditorView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by zhangchao_a on 2016/10/13.
 */

public class LoginActivity extends BaseActionBarActivity implements View.OnClickListener {
    private EditorView mLoginAccountEdt;
    private EditorView mLoginPwdEdt;
    private Button mLoginSubmitBtn;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initView() {
        mLoginAccountEdt= (EditorView)findViewById(R.id.mLoginAccountEdt);
        mLoginPwdEdt= (EditorView)findViewById(R.id.mLoginPwdEdt);
        mLoginSubmitBtn= (Button)findViewById(R.id.mLoginSubmitBtn);
    }

    @Override
    public void initData() {
        mLoginAccountEdt.setText(PrefsAccessor.getInstance(this).getString(Constants.KEY_ACCOUNT));
        mLoginPwdEdt.setText(PrefsAccessor.getInstance(this).getString(Constants.KEY_PASSWORD));

        mLoginSubmitBtn.setOnClickListener(this);
    }

    protected void goHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.mLoginSubmitBtn:
                String account=mLoginAccountEdt.getText().toString();
                String pwd=mLoginPwdEdt.getText().toString();
                if (TextUtil.isValidate(account,pwd))
                {
                    doLogin(account,pwd);
                }
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
                IMApplication.mAppState=1;
                IMApplication.setProfile(profile);
                PrefsAccessor.getInstance(LoginActivity.this).saveString(Constants.KEY_ACCOUNT,account);
                PrefsAccessor.getInstance(LoginActivity.this).saveString(Constants.KEY_PASSWORD,pwd);
                Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
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
