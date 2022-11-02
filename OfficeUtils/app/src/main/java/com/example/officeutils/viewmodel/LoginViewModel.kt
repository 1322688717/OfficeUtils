package com.example.officeutils.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.officeutils.bean.BeanLogin
import com.example.officeutils.https.RequestResponse
import com.example.officeutils.utils.RouterUtil
import com.tencent.mmkv.MMKV
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel : ViewModel() {

//    var userInfo = MutableLiveData<UserInfoBean>()
//    var loginResult = MutableLiveData<LoginRespones>()
//    var VerCode = MutableLiveData<String>()      //验证码
//    var uuid = MutableLiveData<String>()
//    var isclick = MutableLiveData<Boolean>()
//    var beanGologin = BeanGologin()
//
//
//
    /**
     * 登录
     */
    fun login(account : String , password : String,code: String,mActivity: Activity){
        RequestResponse.huaoService.login(account,password).enqueue(object : Callback<BeanLogin?> {
            override fun onResponse(call: Call<BeanLogin?>, response: Response<BeanLogin?>) {
                when (response.body()!!.code) {
                    200 -> {
                        RouterUtil().goBottomNavigationActivity(mActivity)
                        Toast.makeText(mActivity, "登录成功", Toast.LENGTH_SHORT).show()
                        val kv = MMKV.defaultMMKV()
                        kv.encode("token", response.body()!!.token.toString())
                        mActivity.finish()
                    }
                    500 -> {
                        // Toast.makeText(mActivity, response.body()!!.,Toast.LENGTH_SHORT).show()
                    }
                    405 -> {
                        //Toast.makeText(mActivity,response.body()!!.msg,Toast.LENGTH_SHORT).show()
                    }
                    else -> {
                        //Toast.makeText(mActivity,response.body()!!.msg,Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<BeanLogin?>, t: Throwable) {
                Log.e("TAG", "t==$t")
            }
        })
    }
}

//
//            override fun onFailure(call: Call<BeanLogin?>, t: Throwable) {
//                Toast.makeText(activity,t.toString(),Toast.LENGTH_SHORT).show()
//            }
//
//        })
//    }
//
//
//    /**
//     * 设置登录按钮
//     */
//    fun setIsClick(account : String , password : String ,code: String){
//        if (isUserName(account)){
//            if (isPassword(password)){
//                isclick.value = isCode(code)
//            }else{
//                isclick.value = false
//            }
//        }else{
//            isclick.value = false
//        }
//    }
//
//    fun getIsClick() : LiveData<Boolean>{
//        return isclick
//    }
//
//    /**
//     * 判断是否是账号
//     */
//    fun isUserName(account: String) : Boolean{
//        return account.isNotEmpty()
//    }
//
//    /**
//     * 判断是否是密码
//     */
//    fun isPassword(password: String) : Boolean{
//        return password.length > 5
//    }
//
//    /**
//     * 判断是否有验证码
//     */
//    fun isCode(code: String) : Boolean{
//        return code.isNotEmpty()
//    }
//
//    /**
//     * 获取验证码
//     */
//    fun getCode(){
//        RequestResponse.huaoService.getCode().enqueue(object : Callback<BeanCode>{
//            override fun onResponse(call: Call<BeanCode>, response: Response<BeanCode>) {
//                VerCode.value = response.body()!!.img
//                uuid.value = response.body()!!.uuid
//            }
//
//            override fun onFailure(call: Call<BeanCode>, t: Throwable) {
//                TODO("Not yet implemented")
//            }
//        })
//    }