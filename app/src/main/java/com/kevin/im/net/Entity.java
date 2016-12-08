package com.kevin.im.net;

import android.util.JsonReader;

/**
 * Created by ZhangChao on 2016/9/22.
 */

public interface Entity {
    void readFromJson(JsonReader reader) throws AppException;
}
