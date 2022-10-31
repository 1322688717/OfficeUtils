package com.example.officeutils.utils;

import android.content.Intent;
import android.os.Environment;
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
import java.io.InputStream;
import java.io.OutputStream;
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
     * @param base64Code
     * @param fileName 保存的文件名称
     */
    public static void saveFileName(String base64Code, String fileName) {
        //创建文件夹，Environment.getExternalStorageDirectory().getPath()获取根目录，如果文件夹有则不创建，没有，则创建
        //路径  storage/emulated/0/Download/world转pfd
        String pictureFilePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath() + "/world转pfd";
        Log.e(TAG, "pictureFilePath=" + pictureFilePath);
        File pictureFile = new File(pictureFilePath);
        if (!pictureFile.exists()) {
            try {
                pictureFile.mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (pictureFile.exists()) {
                    Log.e(TAG, "pictureFile创建成功");
                } else {
                    Log.e(TAG, "pictureFile创建失败");
                }
            }
        } else {
            Log.e(TAG, "pictureFile存在，地址=" + pictureFilePath);
        }

        String inFile = pictureFilePath + fileName;
        InputStream in = null;
        OutputStream out = null;
        try {
            File outFile = new File(pictureFilePath + fileName);
            if (outFile.exists()) {
                Log.e(TAG, "outFile已经存在");
            } else {
                if (outFile.createNewFile()) {
                    Log.e(TAG, "创建文件" + outFile.getPath() + "成功");
                } else {
                    Log.e(TAG, "创建文件" + outFile.getPath() + "失败");
                }
            }

            in = new FileInputStream(inFile);
            out = new FileOutputStream(outFile);

            byte[] buffer = Base64.decode(base64Code, Base64.DEFAULT);;     //创建字节数组
            int read;
            while ((read = in.read(buffer)) != -1) {        //循环读取数据并且将数据写入到缓存输出流中
                out.write(buffer, 0, read);
                out.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();       //关闭输入流
                } catch (IOException e) {
                    //NOOP
                }
            }
            if (out != null) {
                try {
                    out.close();         //关闭输出流
                } catch (IOException e) {
                    //NOOP
                }
            }
        }
    }
}
