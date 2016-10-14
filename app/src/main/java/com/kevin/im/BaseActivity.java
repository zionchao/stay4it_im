package com.kevin.im;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by zhangchao_a on 2016/10/13.
 */

public abstract class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        initView();
        initData();
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
