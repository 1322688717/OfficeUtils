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
import java.io.DataOutputStream
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
    private val pdfData = ArrayList<PDFFileInfo>()

    var file : String = ""
     var base64 : String = ""
    lateinit var uri : Uri
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

        binding.btnUpload.setOnClickListener {

            thread {
                initData()
                //getPost()
                //test(file)
            }

        }


        binding.btnTest.setOnClickListener {
            mGetContent.launch("application/pdf")

        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun test(file: String) {
        Test.getInstance().main(file);
    }

    private fun getPost() {
        var request: Request? = null
        var okHttpClient: OkHttpClient? = null
        val TAG = "zcq"
        //云市场分配的密钥Id
        val secretId = "AKID7KgduqScdf0kqc75jqn98p1Si0hkdyp73p7G"
        //云市场分配的密钥Key
        val secretKey = "lq8P1959iVGX9C2zo33kIG5VoO01P5jht7hO2ai"
        val source = "market"
        val method = "POST"


        // 查询参数

        val queryParams: MutableMap<String, String> = HashMap()
        var theBase64 = "data:application/pdf;base64,$base64"
        queryParams["document_url"] = "theBase64"
        // url参数拼接
        var url = "https://service-jnj94q93-1255058406.sh.apigw.tencentcs.com/release/convert"
        if (!queryParams.isEmpty()) {
            url += "?" + urlencode(queryParams)
        }


        val cd = Calendar.getInstance()
        val sdf = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US)
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"))
        val datetime: String = sdf.format(cd.getTime())

        val auth: String = calcAuthorization(source, secretId, secretKey, datetime)!!

        val headers: MutableMap<String, String> = HashMap()
        headers["X-Source"] = source
        headers["X-Date"] = datetime
        headers["Authorization"] = auth
        // request body
        // request body
        val methods: MutableMap<String, Boolean> = HashMap()
        methods["POST"] = true
        methods["PUT"] = true
        methods["PATCH"] = true
        val hasBody = methods[method]
//        if (hasBody != null) {
//            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
//            conn.setDoOutput(true)
//            val out = DataOutputStream(conn.getOutputStream())
//            out.writeBytes(urlencode(bodyParams))
//            out.flush()
//            out.close()
//        }

        request = Request.Builder()
            .addHeader("Source",source)
            .addHeader("X-Date",datetime)
            .addHeader("Authorization",auth)
            .url(url)
            .build()

        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(10, TimeUnit.MINUTES)
            .writeTimeout(10, TimeUnit.MINUTES)
            .readTimeout(10, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true).build()


        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("TAG","请求失败——》$e")
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                Log.i("TAG","请求-——》${response}")
                Log.i("TAG","${response.body}")
            }
        })
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





    /**
     * 获取手机文档数据
     *
     * @param
     */
    fun getDocumentData() {
        val columns = arrayOf(
            MediaStore.Files.FileColumns._ID,
            MediaStore.Files.FileColumns.MIME_TYPE,
            MediaStore.Files.FileColumns.SIZE,
            MediaStore.Files.FileColumns.DATE_MODIFIED,
            MediaStore.Files.FileColumns.DATA
        )
        val select = "(_data LIKE '%.pdf')"
        val contentResolver = contentResolver
        val cursor = contentResolver.query(
            MediaStore.Files.getContentUri("external"),
            columns,
            select,
            null,
            null
        )
        var columnIndexOrThrow_DATA = 0
        if (cursor != null) {
            columnIndexOrThrow_DATA =
                cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
        }
        if (cursor != null) {
            while (cursor.moveToNext()) {
                val path = cursor.getString(columnIndexOrThrow_DATA)
                val document: PDFFileInfo = PDFUtil.getFileInfoFromFile(File(path))
                pdfData.add(document)
                Log.d(TAG, " pdf $document")
            }
        }
        cursor!!.close()
    }



    private fun initData() {
        var request: Request? = null
        var okHttpClient: OkHttpClient? = null
        val TAG = "zcq"
        //云市场分配的密钥Id
        val secretId = "AKID7KgduqScdf0kqc75jqn98p1Si0hkdyp73p7G"
        //云市场分配的密钥Key
        val secretKey = "lq8P1959iVGX9C2zo33kIG5VoO01P5jht7hO2ai"
        val source = "market"


        // 查询参数

        val queryParams: MutableMap<String, String> = HashMap()
        var theBase64 = "data:application/pdf;base64,$base64"
        queryParams["document_url"] = theBase64
        // url参数拼接
        var url = "https://service-jnj94q93-1255058406.sh.apigw.tencentcs.com/release/convert"
        if (!queryParams.isEmpty()) {
            url += "?" + urlencode(queryParams)
        }


        val cd = Calendar.getInstance()
        val sdf = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US)
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"))
        val datetime: String = sdf.format(cd.getTime())

        val auth: String = calcAuthorization(source, secretId, secretKey, datetime)!!

        val headers: MutableMap<String, String> = HashMap()
        headers["X-Source"] = source
        headers["X-Date"] = datetime
        headers["Authorization"] = auth


            request = Request.Builder()
                .addHeader("Source",source)
                .addHeader("X-Date",datetime)
                .addHeader("Authorization",auth)
                .url(url)
                .build()

        okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(10, TimeUnit.MINUTES)
            .writeTimeout(10, TimeUnit.MINUTES)
            .readTimeout(10, TimeUnit.MINUTES)
            .retryOnConnectionFailure(true).build()


        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("TAG","请求失败——》$e")
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                Log.i("TAG","请求-——》${response}")
                Log.i("TAG","${response.body}")
            }
        })
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