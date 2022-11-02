package com.example.officeutils.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.officeutils.R
import com.example.officeutils.utils.StatusBarUtil
import com.tencent.mmkv.MMKV


open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        StatusBarUtil.initStatusBarDark(this)



    }
}