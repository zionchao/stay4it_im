package com.kevin.im.db;

import com.kevin.im.IMApplication;

/**
 * Created by zhangchao_a on 2016/10/13.
 */

public class DBController {
    private static DBController instance;
    private OrmDBHelper mDBHelper;

    private DBController()
    {
        mDBHelper=new OrmDBHelper(IMApplication.gContext);
        mDBHelper.getWritableDatabase();
    }

    public static DBController getInstance()
    {
        if (instance==null)
        {
            instance=new DBController();
        }
        return instance;
    }

    public OrmDBHelper getDB()
    {
        return mDBHelper;
    }

}
