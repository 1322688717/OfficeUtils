package com.example.officeutils.utils

import android.content.Context
import android.content.Intent
import com.example.officeutils.ui.BottomNavigationActivity
import com.example.officeutils.ui.FileConversionActivity
import com.example.officeutils.ui.MainActivity
import com.example.officeutils.ui.login.LoginActivity
import com.example.officeutils.ui.login.RegisterActivity

class  RouterUtil {


//    fun goPersonalCenterActivity(mContext: Context){
//        val intent  = Intent(mContext, PersonalCenterActivity::class.java)
//        mContext.startActivity(intent)
//    }
//
//    fun goWallPaperActivity(mContext: Context){
//        val intent  = Intent(mContext, WallPaperActivity::class.java)
//        mContext.startActivity(intent)
//    }
//
//    fun goSetNameActivity(mContext: Context){
//        val intent  = Intent(mContext, SetNameActivity::class.java)
//        mContext.startActivity(intent)
//    }
//
//    fun goMainActivity(mContext: Context,token : String){
//        val intent  = Intent(mContext, MainActivity::class.java)
//        intent.putExtra("token",token)
//        mContext.startActivity(intent)
//    }
    fun goMainActivity(mContext: Context){
        val intent  = Intent(mContext, MainActivity::class.java)
        mContext.startActivity(intent)
    }
//
    fun goLoginActivity(mContext: Context){
        val intent  = Intent(mContext, LoginActivity::class.java)
        mContext.startActivity(intent)
    }

    fun goRegisterActivity(mContext: Context){
        val intent  = Intent(mContext, RegisterActivity::class.java)
        mContext.startActivity(intent)
    }

    fun goBottomNavigationActivity(mContext: Context){
        val intent  = Intent(mContext, BottomNavigationActivity::class.java)
        mContext.startActivity(intent)
    }

    fun goFileConversionActivity(mContext: Context){
        val intent  = Intent(mContext, FileConversionActivity::class.java)
        mContext.startActivity(intent)
    }
//
//    fun goShopInformationActivity(mContext: Context){
//        val intent  = Intent(mContext, ShopInformationActivity::class.java)
//        mContext.startActivity(intent)
//    }

}