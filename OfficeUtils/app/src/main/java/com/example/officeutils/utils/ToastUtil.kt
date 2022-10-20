package com.example.officeutils.utils

import android.content.Context
import android.widget.Toast

class ToastUtil {
    fun ToastLong(mContext: Context,string: String) {
        Toast.makeText(mContext,string,Toast.LENGTH_LONG).show()
    }

    fun ToastShort(mContext: Context,string: String) {
        Toast.makeText(mContext,string,Toast.LENGTH_SHORT).show()
    }
}