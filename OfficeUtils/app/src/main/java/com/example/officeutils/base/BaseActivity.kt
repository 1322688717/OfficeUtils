package com.example.officeutils.base

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.officeutils.R
import com.example.officeutils.utils.StatusBarUtil

open class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)

        StatusBarUtil.initStatusBarDark(this)

    }
}