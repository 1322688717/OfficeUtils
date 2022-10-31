package com.example.officeutils.viewmodel

import android.app.Activity
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.officeutils.bean.BeanRegister
import com.example.officeutils.bean.RequestRegiseter
import com.example.officeutils.https.RequestResponse
import com.example.officeutils.utils.ToastUtil
import io.grpc.Context
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class RegisterViewModel : ViewModel() {
    val phoneNumber = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val isGetCode = MutableLiveData<String>()



    fun register(rr : RequestRegiseter,mContext : Activity){
        RequestResponse.huaoService.register(rr).enqueue(object : retrofit2.Callback<BeanRegister>{
            override fun onResponse(call: Call<BeanRegister>, response: Response<BeanRegister>) {
             if (response.code() == 200){
                 ToastUtil().ToastShort(mContext,"注册成功")
             }else if (response.code() == 500){
                 Log.e("TAG","message==${response.message()}")
             }
            }

            override fun onFailure(call: Call<BeanRegister>, t: Throwable) {
                Log.e("TAG","$t")
            }

        })
    }


}