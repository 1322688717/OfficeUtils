package com.example.officeutils.ui

import android.os.Bundle
import com.example.officeutils.R
import com.example.officeutils.base.BaseActivity
import com.example.officeutils.utils.RouterUtil


class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        RouterUtil().goLoginActivity(this)
        finish()

    }
}