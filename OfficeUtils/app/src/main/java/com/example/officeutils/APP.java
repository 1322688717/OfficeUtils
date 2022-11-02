package com.example.officeutils;

import android.app.Application;

import com.tencent.mmkv.MMKV;

public class APP extends Application {
    @Override
    public void onCreate() {
        super.onCreate();


        String rootDir = MMKV.initialize(this);

    }
}
