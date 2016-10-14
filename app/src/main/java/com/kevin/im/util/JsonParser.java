package com.kevin.im.util;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by zhangchao_a on 2016/10/14.
 */

public class JsonParser {
    private static Gson gson = new Gson();


    public static <T> T deserializeFromJson(String json, Class<T> clz){
        return gson.fromJson(json, clz);
    }

    public static <T> T deserializeFromJson(String json, Type type){
        return gson.fromJson(json, type);
    }


    public static String serializeToJson(Object object){
        return gson.toJson(object);
    }
}
