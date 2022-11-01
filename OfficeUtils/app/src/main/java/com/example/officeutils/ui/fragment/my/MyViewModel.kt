package com.example.officeutils.ui.fragment.my

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.officeutils.https.RequestResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyViewModel : ViewModel() {

    var headPortrait = MutableLiveData<String>()
    var nickname =  MutableLiveData<String>()
    var IndSignature = MutableLiveData<String>()  //个性签名
    var avatar = MutableLiveData<String>()  //头像
    var logoutMsg = MutableLiveData<String>()  //退出登录toast

    init {
        nickname.value = ""
        IndSignature.value = "这个人很懒，什么都没留下..."
    }
    fun setHeadPortrait(mcontext : FragmentActivity) {

    }
//
//    fun getNickname(activity : Activity) {
//        val sharedPreferences: SharedPreferences = activity.getSharedPreferences("sp", Context.MODE_PRIVATE)
//        val token = sharedPreferences.getString("token", "")
//        RequestResponse.huaoService.getUserInfo(token!!).enqueue(object : Callback<Userinfo> {
//            override fun onResponse(call: Call<Userinfo>, response: Response<Userinfo>) {
//                nickname.value =  response.body()!!.data.nickName
//            }
//
//            override fun onFailure(call: Call<Userinfo>, t: Throwable) {
//                Toast.makeText(activity,t.toString(), Toast.LENGTH_LONG).show()
//            }
//        })
//    }
//
//    /**
//     * 获取用户头像
//     */
//    fun getAvatar(activity : Activity){
//        val sharedPreferences: SharedPreferences = activity.getSharedPreferences("sp", Context.MODE_PRIVATE)
//        val token = sharedPreferences.getString("token", "")
//        RequestResponse.huaoService.getUserInfo(token!!).enqueue(object : Callback<Userinfo> {
//            override fun onResponse(call: Call<Userinfo>, response: Response<Userinfo>) {
//                avatar.value =  response.body()!!.data.avatar
//            }
//
//            override fun onFailure(call: Call<Userinfo>, t: Throwable) {
//                Toast.makeText(activity,t.toString(), Toast.LENGTH_LONG).show()
//            }
//        })
//    }
//
//    /**
//     * 退出登录
//     */
//    fun logout(activity : Activity){
//        RequestResponse.huaoService.logout().enqueue(object : Callback<BeanLogout> {
//            override fun onResponse(call: Call<BeanLogout>, response: Response<BeanLogout>) {
//                logoutMsg.value = response.body()!!.msg
//            }
//
//            override fun onFailure(call: Call<BeanLogout>, t: Throwable) {
//                Toast.makeText(activity,t.toString(), Toast.LENGTH_LONG).show()
//            }
//
//        })
//    }
}