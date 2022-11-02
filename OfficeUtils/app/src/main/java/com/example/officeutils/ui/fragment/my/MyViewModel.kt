package com.example.officeutils.ui.fragment.my

import BeanUserInfo
import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.fastjson2.JSON
import com.example.officeutils.https.OkHttpUtils
import com.google.gson.Gson
import com.google.protobuf.StringValue
import com.google.protobuf.StringValueOrBuilder
import com.tencent.mmkv.MMKV
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import okhttp3.Call
import okhttp3.Response


class MyViewModel : ViewModel() {

    val TAG = "MyViewModel"
    var userInfo = MutableLiveData<BeanUserInfo>()

    init {


    }

    /**
     * 获取用户信息
     */
    suspend fun getUserInfo(activity : Activity) {
        withContext(Dispatchers.IO){


        val kv = MMKV.defaultMMKV()
        val token = kv.decodeString("token")

        OkHttpUtils.builder()
            .url("http://1.12.240.180:8083/androidServer/sys-user/list")
            .addHeader("token", token)
            .addParam("phone", "13500000000")
            .post(true)
            .async(object : OkHttpUtils.ICallBack {
                override fun onSuccessful(call: Call?, data: Response){
                    //Log.i(TAG, "onSuccessful: "+"data.message"+"/////"+ data.body!!.string())

                   // userInfo.postValue(Gson().fromJson(data.body.toString(),BeanUserInfo::class.java))
                   userInfo.postValue(JSON.parseObject(data?.body?.string(), BeanUserInfo::class.java))
                }

                override fun onFailure(call: Call?, errorMsg: String?) {
                    Log.e("TAG", "errorMsg==$errorMsg")
                }

            })
        }
    }
}