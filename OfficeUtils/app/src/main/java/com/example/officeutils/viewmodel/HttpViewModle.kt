package com.example.officeutils.viewmodel

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.fastjson.JSON
import com.example.officeutils.bean.Base64ToFile
import com.example.officeutils.https.OkHttpUtils
import com.example.officeutils.https.PdfConvertHelp
import com.example.officeutils.ui.Java_Windows
import com.example.officeutils.utils.FileToBase64ToFile
import com.example.officeutils.utils.UriToFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Call
import okhttp3.Response

class HttpViewModle : ViewModel() {
    val TAG = "HttpViewModle"
    //测试接口的数据
    val theResult = MutableLiveData<String>()
    //pdf转换的数据
    val pdfConvert = MutableLiveData<String?>()
    // 接收的body信息
    val baseToFile = MutableLiveData<Base64ToFile>()
    //文件地址
    val file = MutableLiveData<String>()
    //发送的base64
    val requestBase64 = MutableLiveData<String>()
    //发送的参数
    var jsonToFrom = MutableLiveData<String>()


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
                        Log.i(TAG, "========="+"onFailure: $errorMsg")
                        theResult.postValue(errorMsg)
                    }

                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onSuccessful(call: Call?, data: Response) {
                        Log.i(TAG, "=================="+"onSuccessful: $data")
                        theResult.postValue(data.message.toString())
                    }
                })
        }
    }

    /**
     *文件转换  post请求
     */
    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun postPdfConvert(){
        withContext(Dispatchers.IO) {
            OkHttpUtils.builder().url(PdfConvertHelp.url)
                .addHeader("X-Source", PdfConvertHelp.source)
                .addHeader("X-Date", PdfConvertHelp.GetGMT())
                .addHeader("Authorization", PdfConvertHelp.calcAuthorization(
                    PdfConvertHelp.source, PdfConvertHelp.secretId, PdfConvertHelp.secretKey, PdfConvertHelp.GetGMT()))
                .addParam("body",jsonToFrom.value)
                .post(false)
                .async(object : OkHttpUtils.ICallBack {
                    override fun onSuccessful(call: Call?, data: Response) {
                        pdfConvert.postValue(data!!.toString())
                        Log.i(TAG, "onSuccessful: ${data.message.toString()}${data.isSuccessful}${data.code}${data.body}")
                        baseToFile.postValue(JSON.parseObject(data.body.toString(),Base64ToFile::class.java))
                    }

                    override fun onFailure(call: Call?, errorMsg: String?) {
                        Log.i(TAG, "onFailure: $errorMsg")
                        pdfConvert.postValue(errorMsg)
                    }
                })
        }
    }

    /**
     * 构建请求参数
     */
    fun pliceParameters(){
         jsonToFrom.postValue(PdfConvertHelp.LoadingEntityClasses(requestBase64.value))
    }


    /**
     * demo的post请求
     */
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun theDemoPOst(file:String,context: Context){
        withContext(Dispatchers.IO){
            baseToFile.postValue(Java_Windows.mains(file,context))
        }
    }

    /**
     * 获取uri
     */
    suspend fun  getFile(context: Context, uri : Uri){
        var FILE : String
        withContext(Dispatchers.IO){
            FILE = UriToFile.UriToF(context,uri)
        }
        Log.i(ContentValues.TAG, "getFile:$FILE ")
        file.postValue(FILE)
        getBase64(FILE)
    }

    /**
     * 获取base64
     */
    suspend fun getBase64(file : String){
        var BASE_64 : String
        withContext(Dispatchers.IO){
            BASE_64 = FileToBase64ToFile.encodeBase64File(file)
        }
        requestBase64.postValue(BASE_64)
    }


}
