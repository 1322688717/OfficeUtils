package com.example.officeutils.ui

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.officeutils.bean.PDFFileInfo
import com.example.officeutils.databinding.ActivityFileConversionBinding
import com.example.officeutils.https.OkHttpUtils
import com.example.officeutils.https.TheHttpPrams
import com.example.officeutils.utils.Base64Encoder
import com.example.officeutils.utils.FileToBase64
import com.example.officeutils.utils.PDFUtil
import com.example.officeutils.utils.UriToFile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import java.io.File
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.URLEncoder
import java.security.InvalidKeyException
import java.security.Key
import java.security.NoSuchAlgorithmException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.concurrent.thread


class FileConversionActivity : AppCompatActivity() {

    lateinit var binding: ActivityFileConversionBinding

    var file : String = ""
     var base64 : String = ""
    lateinit var uri : Uri
    private val secretId = "AKID7KgduqScdf0kqc75jqn98p1Si0hkdyp73p7G"
    //云市场分配的密钥Key
    private val secretKey = "lq8P1959iVGX9C2zo33kIG5VoO01P5jht7hO2ai"
    val source = "market"
    val url = "https://service-1odt5k7b-1255058406.sh.apigw.tencentcs.com/release/v2/jobs"

    lateinit var textView : TextView

    //对文件管理器返回值做处理
    var mGetContent = registerForActivityResult<String, Uri>(ActivityResultContracts.GetContent()) { uri1 ->
        // Handle the returned Uri
        uri = uri1
        Log.i(TAG, "onActivityResult:$uri")
        GlobalScope.launch {
             file = getFile(this@FileConversionActivity,uri)
             base64 = getBase64(file)
            val sn = "长度："+base64.length.toString()+"\n"+ "显示base64前2000位" +"\n"+uri+"\n"+file
            //主线程操作
            withContext(Dispatchers.Main){
                Log.e("TAG","$sn")
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileConversionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /**
         * 获取文件
         */
        binding.btnTest.setOnClickListener {
            mGetContent.launch("application/pdf")
        }

        /**
         * 发送请求
         */
        binding.btnUpload.setOnClickListener {
            GlobalScope.launch {
                Post(base64)
                PostTest()
            }
        }



    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun test(file: String) {
        Test.getInstance().main(file);
    }

    /**
     *文件转换  post请求
     */
    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun Post(base:String){
        withContext(Dispatchers.IO) {
            OkHttpUtils.builder().url(url)
                .addHeader("Content-Type","application/x-www-form-urlencoded")
                .addHeader("X-Source", source)
                .addHeader("X-Date", TheHttpPrams.GetGMT())
                .addHeader("Authorization", TheHttpPrams.calcAuthorization(source, secretId, secretKey, TheHttpPrams.GetGMT()))
                .addParam("",base)
                .post(false)
                .async(object : OkHttpUtils.ICallBack {
                    override fun onSuccessful(call: Call?, data: String?) {
                        Log.i(TAG, "onSuccessful: $data")
                    }

                    override fun onFailure(call: Call?, errorMsg: String?) {
                        Log.i(TAG, "onFailure: $errorMsg")
                    }
                })
        }
    }

    /**
     * 测试 HTTP post请求
     */
    @RequiresApi(Build.VERSION_CODES.N)
    suspend fun PostTest(){
        withContext(Dispatchers.IO){
            OkHttpUtils.builder().url("http://jsonplaceholder.typicode.com/posts")
                .addHeader("Content-Type","application/x-www-form-urlencoded")
                .addParam("userId","123")
                .addParam("title","ahdfluh")
                .addParam("body","jdkhfa")
                .post(false)
                .async(object : OkHttpUtils.ICallBack{
                    override fun onFailure(call: Call?, errorMsg: String?) {
                        Log.i(TAG, "========="+"onFailure: $errorMsg")
                    }

                    override fun onSuccessful(call: Call?, data: String?) {
                        Log.i(TAG, "=================="+"onSuccessful: $data")
                    }
                })
        }
    }

    suspend fun  getFile(context: Context, uri : Uri):String{
        var FILE : String
        withContext(Dispatchers.IO){
            FILE = UriToFile.UriToF(context,uri)
        }
        Log.i(TAG, "getFile:$FILE ")
        return FILE
    }

    suspend fun getBase64(file : String):String{
        var BASE_64 : String
        withContext(Dispatchers.IO){
            BASE_64 = FileToBase64.encodeBase64File(file)
        }
        Log.i("getBase64", "getBase64: $BASE_64")
        return BASE_64
    }


    @Throws(UnsupportedEncodingException::class)
    fun urlencode(map: Map<*, *>): String? {
        val sb = StringBuilder()
        for ((key, value)in map) run {
            if (sb.length > 0) {
                sb.append("&")
            }
            sb.append(
                java.lang.String.format(
                    "%s=%s",
                    URLEncoder.encode(key.toString(), "UTF-8"),
                    URLEncoder.encode(value.toString(), "UTF-8")
                )
            )
        }
        return sb.toString()
    }


    @Throws(
        NoSuchAlgorithmException::class,
        UnsupportedEncodingException::class,
        InvalidKeyException::class
    )
    fun calcAuthorization(
        source: String,
        secretId: String,
        secretKey: String,
        datetime: String
    ): String? {
        val signStr = "x-date: $datetime\nx-source: $source"
        val mac: Mac = Mac.getInstance("HmacSHA1")
        val sKey: Key = SecretKeySpec(secretKey.toByteArray(charset("UTF-8")), mac.getAlgorithm())
        mac.init(sKey)
        val hash: ByteArray = mac.doFinal(signStr.toByteArray(charset("UTF-8")))

        val sig: String = Base64Encoder.encode(hash)

        return "hmac id=\"$secretId\", algorithm=\"hmac-sha1\", headers=\"x-date x-source\", signature=\"$sig\""
    }


}