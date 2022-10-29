package com.example.officeutils.ui

import android.content.ContentValues.TAG
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.officeutils.databinding.ActivityFileConversionBinding
import com.example.officeutils.utils.FileToBase64
import com.example.officeutils.utils.UriToFile
import com.example.officeutils.viewmodel.HttpViewModle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.UnsupportedEncodingException
import java.net.URLEncoder


class FileConversionActivity : AppCompatActivity() {

    lateinit var binding: ActivityFileConversionBinding
    lateinit var viewModel: HttpViewModle
    val mutableMap = mutableMapOf<String,String>()

    var file : String = ""
     var base64 : String = ""
    lateinit var uri : Uri

    //对文件管理器返回值做处理
    var mGetContent = registerForActivityResult<String, Uri>(ActivityResultContracts.GetContent()) { uri1 ->
        // Handle the returned Uri
        uri = uri1
        Log.i(TAG, "onActivityResult:$uri")
        GlobalScope.launch {
             file = getFile(this@FileConversionActivity,uri)
             val base = getBase64(file)
            mutableMap.put("document_url","data:application/pdf;base64,$base")
            base64 = urlencode(mutableMap)
            val sn = "长度："+base64.length.toString()+"\n" +"\n"+uri+"\n"+file
            Log.e("TAG","$sn")
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileConversionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(HttpViewModle::class.java)
        viewModel.pdfConvert.observe(this){pdfConvert ->
            binding.btnResult.text = pdfConvert.toString()
        }

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
                //测试的post请求
                viewModel.postTest()
                viewModel.postPdfConvert(base64)
            }
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun test(file: String) {
        Test.getInstance().main(file);
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
    fun urlencode(map: Map<*, *>): String {
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
}