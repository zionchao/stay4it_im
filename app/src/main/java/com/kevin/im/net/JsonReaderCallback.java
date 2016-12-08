package com.kevin.im.net;

import android.util.JsonReader;

import java.io.FileReader;
import java.lang.reflect.ParameterizedType;

/**
 * Created by ZhangChao on 2016/9/22.
 */

public abstract class JsonReaderCallback<T extends Entity> extends AbstractCallback<T> {

    @Override
    protected T bindData(String path) throws AppException {

        try {
            FileReader fileReader=new FileReader(path);
            JsonReader reader=new JsonReader(fileReader);
            ParameterizedType parameterizedType = (ParameterizedType)this.getClass().getGenericSuperclass();
            Class<T> entityClass = (Class<T>) (parameterizedType.getActualTypeArguments()[0]);
            T t=entityClass.newInstance();
            t.readFromJson(reader);
            return t;
            //reader.beginObject();
//            while ( reader.hasNext()) {
//                String name = reader.nextName();
//                if (name.equals("data")) {
//                    t.readFromJson(reader);
//                }else
//                    reader.skipValue();
//            }

//            while ( reader.hasNext())
//            {
//                String name= reader.nextName();
//                if (name.equals("data"))
//                {
//                     reader.beginArray();
//                    while ( reader.hasNext()){
//                         reader.beginObject();
//                        while( reader.hasNext())
//                        {
//                            t.readFromJson(reader);
//                        }
//                         reader.endObject();
//
//                    }
//                     reader.endArray();
//                }
//            }
            // reader.endObject();

        } catch (Exception e) {
            throw new AppException(AppException.ErrorType.IO,e.getMessage());
        }
//        return null;
    }
}
