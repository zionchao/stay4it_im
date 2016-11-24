package com.kevin.im.home;

import android.content.Intent;

import com.kevin.im.BaseActionBarActivity;
import com.kevin.im.BaseFragment;
import com.kevin.im.R;
import com.kevin.im.entities.Tab;
import com.kevin.im.push.IMPushManager;
import com.kevin.im.util.Constants;
import com.kevin.im.widget.TabIndictor;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by zhangchao_a on 2016/11/21.
 */
public class HomeActivity extends BaseActionBarActivity implements TabIndictor.OnTabClickListener {

    private TabIndictor mHomeIndicator;
    private ArrayList<Tab> tabs;
    private BaseFragment mCurrentFragment;


    @Override
    public void setContentView() {
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getBooleanExtra(Constants.KEY_FORCE_QUIT,false))
        {
            finish();
        }
    }

    @Override
    protected void protectApp() {
        Intent intent=new Intent(this,WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void initView() {
        mHomeIndicator=(TabIndictor)findViewById(R.id.mHomeIndicator);
        mHomeIndicator.setOnTabClickListener(this);
        tabs=new ArrayList<Tab>();
        tabs.add(new Tab(R.drawable.selector_tab_msg,R.string.mTabMsgLabel,ConversationFragment.class));
        tabs.add(new Tab(R.drawable.selector_tab_contact, R.string.mTabContactLabel,ContactFragment.class));
        tabs.add(new Tab(R.drawable.selector_tab_moments, R.string.mTabMomentsLabel,MomentsFragment.class));
        tabs.add(new Tab(R.drawable.selector_tab_profile, R.string.mTabProfileLabel,ProfileFragment.class));
        mHomeIndicator.initData(tabs);
        mHomeIndicator.setCurrentTab(0);

    }

    @Override
    public void initData() {
        IMPushManager.getInstance(this).startPush();
    }

    @Override
    public void onTabClick(int index) {
        try {
            Constructor<? extends BaseFragment> con=tabs.get(index).getFragment().getConstructor();
            mCurrentFragment=con.newInstance();
            getFragmentManager().beginTransaction().replace(R.id.mHomeFrame,mCurrentFragment).commitAllowingStateLoss();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
