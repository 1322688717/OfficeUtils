package com.example.officeutils.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import com.example.officeutils.R
import com.example.officeutils.base.BaseActivity
import com.example.officeutils.utils.RouterUtil


class SplashActivity : BaseActivity() {

    var token = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val sharedPreferences: SharedPreferences = getSharedPreferences("sp", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", "")

        if (token!!.isNotEmpty()){
            RouterUtil().goBottomNavigationActivity(this)
            finish()
        }else{
            RouterUtil().goLoginActivity(this)
            finish()
        }



    }
}