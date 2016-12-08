package com.kevin.im.net;


import com.kevin.im.entities.Attachment;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

/**
 * Created by zhangchao_a on 2016/9/23.
 */

public class UploadUtil {

    public static void upload(OutputStream out,String filePath) throws AppException {
        String BOUNDARY="7d4a6d158c9";//数据分隔线
        DataOutputStream outputStream=new DataOutputStream(out);
        try{
            outputStream.writeBytes("--"+BOUNDARY+"\r\n");
            outputStream.writeBytes("Content-Disposition:form-data;name=\"file0\";filename="
                    +filePath.substring(filePath.lastIndexOf("/")+1)+"\""+"\r\n");
            outputStream.writeBytes("\r\n");
            byte[] buffer=new byte[1024];
            FileInputStream fis=new FileInputStream(filePath);
            while(fis.read(buffer,0,1024)!=-1){
                outputStream.write(buffer,0,buffer.length);
            }
            fis.close();
            outputStream.write("\r\n".getBytes());
            byte[]end_data=("--"+BOUNDARY+"--\r\n").getBytes();//数据结束标志
            outputStream.write(end_data);
            outputStream.flush();
        } catch (IOException e) {
            throw new AppException(AppException.ErrorType.UPLOAD,e.getMessage());
        }
    }

    public static void upload(OutputStream out, String postContent, ArrayList<Attachment> entities) throws AppException {

            String BOUNDARY="7d4a6d158c9";//数据分隔线
            String PERFIX="--",LINEND="\r\n";
            String CHARSET="UTF-8";
            DataOutputStream outputStream=new DataOutputStream(out);
            try{
                StringBuilder sb=new StringBuilder();
                sb.append(PERFIX);
                sb.append(BOUNDARY);
                sb.append(LINEND);
                sb.append("Content-Disposition:form-data;name=\""+"data"+"\""+LINEND);
                sb.append("Content-Type:text/plain;charset="+CHARSET);
                sb.append("Content-Transfer-Enconding:8bit"+LINEND);
                sb.append(LINEND);
                sb.append(postContent);
                sb.append(LINEND);
                outputStream.write(sb.toString().getBytes());
                int i=0;
                for (Attachment entity:entities)
                {
                    StringBuilder sb1=new StringBuilder();
                    sb1.append(PERFIX);
                    sb1.append(BOUNDARY);
                    sb1.append(LINEND);
                    sb1.append("Content-Disposition:form-data;name=\"+file"+(i++)+"\";filename=\""+entity.getFile_name()+LINEND);
                    sb1.append("Content-Type:"+entity.getFile_type()+LINEND);
                    sb1.append(LINEND);
                    outputStream.write(sb1.toString().getBytes());
                    InputStream is=new FileInputStream(entity.getFile_path());
                    byte[] buffer=new byte[1024];
                    int len=0;
                    while ((len=is.read(buffer))!=-1)
                    {
                        outputStream.write(buffer,0,len);
                    }
                    is.close();
                    outputStream.write(LINEND.getBytes());

                }
                byte[]end_data=(PERFIX+BOUNDARY+PERFIX+LINEND).getBytes();
                outputStream.write(end_data);
            }catch (FileNotFoundException e){
                throw new AppException(AppException.ErrorType.FileNotFoundException,
                        "upload error: the upload file is not exsit."+e.getMessage());
            }
            catch (IOException e) {
               throw new AppException(AppException.ErrorType.FileNotFoundException,e.getMessage());
            }

    }
}
