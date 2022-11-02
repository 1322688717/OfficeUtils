package com.example.officeutils.ui

import android.os.Bundle
import com.example.officeutils.R
import com.example.officeutils.base.BaseActivity
import com.example.officeutils.utils.RouterUtil
import com.tencent.mmkv.MMKV


class SplashActivity : BaseActivity() {

    var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val kv = MMKV.defaultMMKV()
        val token = kv.decodeString("token","")


        if (token!!.isNotEmpty()){
            RouterUtil().goBottomNavigationActivity(this)
            finish()
        }else{
            RouterUtil().goLoginActivity(this)
            finish()
        }



    }
}