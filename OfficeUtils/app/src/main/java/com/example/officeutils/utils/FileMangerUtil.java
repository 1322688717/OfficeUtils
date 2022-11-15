package com.example.officeutils.utils;

import android.content.ContentResolver;
import android.content.Context;

public class FileMangerUtil {
    private static FileMangerUtil mInstance;
    private static Context mContext;
    private static ContentResolver mContentResolver;
    private static Object mLock = new Object();


    public static FileMangerUtil getInstance(Context context){
        if (mInstance == null){
            synchronized (mLock){
                if (mInstance == null){
                    mInstance = new FileMangerUtil();
                    mContext = context;
                    mContentResolver = context.getContentResolver();
                }
            }
        }
        return mInstance;
    }

}
