package com.example.officeutils.viewmodel

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.example.officeutils.bean.JsonToFrom
import com.example.officeutils.https.OkHttpUtils
import com.example.officeutils.https.PdfConvertHelp
import com.example.officeutils.ui.Java_Windows
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.Call

class HttpViewModle : ViewModel() {
    val TAG = "HttpViewModle"
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
                        Log.i(TAG, "========="+"onFailure: $errorMsg")
                        theResult.postValue(errorMsg)
                    }

                    @RequiresApi(Build.VERSION_CODES.O)
                    override fun onSuccessful(call: Call?, data: String) {
                        Log.i(TAG, "=================="+"onSuccessful: $data")
                        theResult.postValue(data)
                    }
                })
        }
    }

    /**
     *文件转换  post请求
     */
    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun postPdfConvert(theBase64: JsonToFrom){
        withContext(Dispatchers.IO) {
            //val theBodyMap = HashMap<String,String>()
            val Body = "{\"tasks\":{\"JsonRootBean\":{\"input\":[\"ImportFile\"],\"output_format\":\"pdf\",\"operation\":\"convert\"},\"ImportFile\":{\"url\":\""+PdfConvertHelp.he+"theBase64"+"\",\"operation\":\"import/url\"},\"ExportResult\":{\"input\":[\"JsonRootBean\"],\"operation\":\"export/url\"}},\"webHook\":\"\",\"tag\":\"\"}"
            //val theBody = PdfConvertHelp.urlencode(theBodyMap)
            val jsons = JSON.parseObject(Body)
            Log.i(TAG, "原本的请求体: $jsons")

            val theRequestJson = JSONObject.toJSONString(theBase64)
            Log.i(TAG, "theRequestJson: $theRequestJson")

            OkHttpUtils.builder().url(PdfConvertHelp.url)
                .addHeader("X-Source", PdfConvertHelp.source)
                .addHeader("X-Date", PdfConvertHelp.GetGMT())
                .addHeader("Authorization", PdfConvertHelp.calcAuthorization(
                    PdfConvertHelp.source, PdfConvertHelp.secretId, PdfConvertHelp.secretKey, PdfConvertHelp.GetGMT()))
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addParam("body",theRequestJson)
                .post(true)
                .async(object : OkHttpUtils.ICallBack {
                    override fun onSuccessful(call: Call?, data: String?) {
                        Log.i(TAG, "onSuccessful: $data")
                        pdfConvert.postValue(data)
                    }

                    override fun onFailure(call: Call?, errorMsg: String?) {
                        Log.i(TAG, "onFailure: $errorMsg")
                        pdfConvert.postValue(errorMsg)
                    }
                })
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun theDemoPOst(file:String){
        withContext(Dispatchers.IO){
            val result = Java_Windows.mains(file).toString()
            Log.i(TAG, "theDemoPOst: $result")
            pdfConvert.postValue(result)
        }
    }

}
