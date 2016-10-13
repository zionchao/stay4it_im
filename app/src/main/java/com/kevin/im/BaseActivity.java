package com.kevin.im;

import android.app.Activity;
import android.os.Bundle;
import android.os.PersistableBundle;

/**
 * Created by zhangchao_a on 2016/10/13.
 */

public abstract class BaseActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        setContentView();
        initView();
        initDate();
    }

    public abstract  void setContentView() ;

    public abstract void initView() ;

    public abstract void initDate();
}
