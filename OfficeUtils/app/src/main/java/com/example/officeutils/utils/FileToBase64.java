package com.example.officeutils.utils;

import android.content.Intent;
import android.provider.DocumentsContract;
import android.util.Base64;
import android.util.Log;

import com.example.officeutils.ui.login.LoginActivity;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class FileToBase64 {

    private static String TAG = "FileToBase64";

    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int)file.length()];
        inputFile.read(buffer);
        inputFile.close();
        Log.i(TAG, "encodeBase64File: 转换成功");
        return Base64.encodeToString(buffer,Base64.NO_WRAP);
        //return urlEncoded(Base64.encodeToString(buffer,Base64.NO_WRAP)) ;
    }

    /**
     * urlEncode编码
     * @param paramString
     * @return
     */
    public static String urlEncoded(String paramString) {
        if (paramString == null || paramString.equals("")) {
            return "";
        }
        try {
            String str = new String(paramString.getBytes(), StandardCharsets.UTF_8);
            str = URLEncoder.encode(str, "UTF-8");
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }



    /**
     * decoderBase64File:(将base64字符解码保存文件). <br/>
     * @author guhaizhou@126.com
     * @param base64Code 编码后的字串
     * @param savePath  文件保存路径
     * @throws Exception
     * @since JDK 1.6
     */
    public static void decoderBase64File(String base64Code,String savePath) throws Exception {
//byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        byte[] buffer =Base64.decode(base64Code, Base64.DEFAULT);
        FileOutputStream out = new FileOutputStream(savePath);
        out.write(buffer);
        out.close();
        Log.i(TAG, "decoderBase64File: 将base64字符解码保存文件结束"+savePath);
    }

    /**
     * @Description:Base64转换成pdf
     * @param:
     * @return:
     * @author: TateBrown
     * @Date: 2018/7/23
     */

    public static void baseToPdfFile(String pdfBase64Str,String filepath){
        BufferedInputStream bis = null;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;
        try{
            byte[] bytes=Base64.decode(pdfBase64Str,Base64.DEFAULT);
            ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(bytes);
            bis=new BufferedInputStream(byteArrayInputStream);
            File file=new File(filepath);
            File path=file.getParentFile();
            if(!path.exists()){
                path.mkdirs();
            }
            fos=new FileOutputStream(file);
            bos=new BufferedOutputStream(fos);

            byte[] buffer=new byte[1024];
            int length=bis.read(buffer);
            while(length!=-1){
                bos.write(buffer,0,length);
                length=bis.read(buffer);
            }
            bos.flush();
            Log.i(TAG, "baseToPdfFile: Base64转换成pdf结束");
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try{
                bis.close();
                bos.close();
                fos.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }


    }
}
