package com.example.officeutils.viewmodel

import android.content.Context
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.JSONObject
import com.example.officeutils.bean.FileNameAndBase64
import com.example.officeutils.bean.JsonToFrom
import com.example.officeutils.bean.ResultBean
import com.example.officeutils.https.OkHttpUtils
import com.example.officeutils.https.PdfConvertHelp
import com.example.officeutils.ui.Java_Windows
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
    //demo的 PDF转换数据
    val demoPdfConvert = MutableLiveData<ResultBean>()
    // 接收的base64+name
    val base64andname = MutableLiveData<FileNameAndBase64>()
    //文件地址
    val file = MutableLiveData<String>()
    //发送的base64
    val requestBase64 = MutableLiveData<String>()
    //发送的参数
    var jsonToFrom = MutableLiveData<JsonToFrom>()


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
            pliceParameters()
            OkHttpUtils.builder().url(PdfConvertHelp.url)
                .addHeader("X-Source", PdfConvertHelp.source)
                .addHeader("X-Date", PdfConvertHelp.GetGMT())
                .addHeader("Authorization", PdfConvertHelp.calcAuthorization(
                    PdfConvertHelp.source, PdfConvertHelp.secretId, PdfConvertHelp.secretKey, PdfConvertHelp.GetGMT()))
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .addParam("",jsonToFrom.toString())
                .post(false)
                .async(object : OkHttpUtils.ICallBack {
                    override fun onSuccessful(call: Call?, data: Response?) {
                        Log.i(TAG, "onSuccessful: $data")
                            pdfConvert.postValue(data!!.toString())

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
        val LIstSrtings : List<String> = listOf("ImportFile")
        val ConvertFileDTO = JsonToFrom.TasksDTO.ConvertFileDTO(LIstSrtings,"pdf","convert")
        val ImportFileDTO = JsonToFrom.TasksDTO.ImportFileDTO("import/url","application/vnd.openxmlformats-officedocument.wordprocessingml.document;base64,$requestBase64")
        val listString : List<String> = listOf("ConvertFile")
        val ExportResultDTO = JsonToFrom.TasksDTO.ExportResultDTO(listString,"export/url")
        val TasksDTO = JsonToFrom.TasksDTO(ConvertFileDTO,ImportFileDTO,ExportResultDTO)
         jsonToFrom.postValue(JsonToFrom("","",TasksDTO))
    }


    /**
     * demo的post请求
     */
    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun theDemoPOst(file:String,context: Context){
        withContext(Dispatchers.IO){
            base64andname.postValue(Java_Windows.mains(file,context))
        }
    }


}
