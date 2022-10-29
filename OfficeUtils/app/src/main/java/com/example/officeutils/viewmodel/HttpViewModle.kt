package com.example.officeutils.viewmodel

import android.content.ContentValues
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.officeutils.https.OkHttpUtils
import com.example.officeutils.https.PdfConvertHelp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Call

class HttpViewModle : ViewModel() {
    //测试接口的数据
    val theResult = MutableLiveData<String>()
    //pdf转换的数据
    val pdfConvert = MutableLiveData<String?>()

    /**
     * 测试 HTTP post请求
     */
    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun postTest(){
        withContext(Dispatchers.IO){
            OkHttpUtils.builder().url("http://jsonplaceholder.typicode.com/posts")
                .addHeader("Content-Type","application/x-www-form-urlencoded")
                .addParam("userId","123")
                .addParam("title","ahdfluh")
                .addParam("body","jdkhfa")
                .post(false)
                .async(object : OkHttpUtils.ICallBack{
                    override fun onFailure(call: Call?, errorMsg: String) {
                        Log.i(ContentValues.TAG, "========="+"onFailure: $errorMsg")
                        theResult.postValue(errorMsg)
                    }

                    override fun onSuccessful(call: Call?, data: String) {
                        Log.i(ContentValues.TAG, "=================="+"onSuccessful: $data")
                        theResult.postValue(data)
                    }
                })
        }
    }

    /**
     *文件转换  post请求
     */
    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun postPdfConvert(base:String){
        withContext(Dispatchers.IO) {
            OkHttpUtils.builder().url(PdfConvertHelp.url)
                .addHeader("Content-Type","application/x-www-form-urlencoded")
                .addHeader("X-Source", PdfConvertHelp.source)
                .addHeader("X-Date", PdfConvertHelp.GetGMT())
                .addHeader("Authorization", PdfConvertHelp.calcAuthorization(
                    PdfConvertHelp.source, PdfConvertHelp.secretId, PdfConvertHelp.secretKey, PdfConvertHelp.GetGMT()))
                .addParam("",base)
                .post(false)
                .async(object : OkHttpUtils.ICallBack {
                    override fun onSuccessful(call: Call?, data: String?) {
                        Log.i(ContentValues.TAG, "onSuccessful: $data")
                        pdfConvert.postValue(data)
                    }

                    override fun onFailure(call: Call?, errorMsg: String?) {
                        Log.i(ContentValues.TAG, "onFailure: $errorMsg")
                        pdfConvert.postValue(errorMsg)
                    }
                })
        }
    }

}
