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
import com.example.officeutils.bean.JsonToFrom
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

    var file : String = ""
     var base64 : String = ""
    lateinit var uri : Uri

    //对文件管理器返回值做处理
    @RequiresApi(Build.VERSION_CODES.N)
    var mGetContent = registerForActivityResult<String, Uri>(ActivityResultContracts.GetContent()) { uri1 ->
        // Handle the returned Uri
        uri = uri1
        Log.i(TAG, "onActivityResult:$uri")
        GlobalScope.launch {
            file = getFile(this@FileConversionActivity,uri)
            base64 = getBase64(file)

            val LIstSrtings : List<String> = listOf("ImportFile")
            val ConvertFileDTO = JsonToFrom.TasksDTO.ConvertFileDTO(LIstSrtings,"pdf","convert")
            val ImportFileDTO = JsonToFrom.TasksDTO.ImportFileDTO("import/url","data:application/pdf;base64,$base64")
            val listString : List<String> = listOf("ConvertFile")
            val ExportResultDTO = JsonToFrom.TasksDTO.ExportResultDTO(listString,"export/url")
            val TasksDTO = JsonToFrom.TasksDTO(ConvertFileDTO,ImportFileDTO,ExportResultDTO)
            val JsonToFrom = JsonToFrom("","",TasksDTO)
            viewModel.postPdfConvert(JsonToFrom)
            val sn = "长度："+base64.length+"\n" +uri+"\n"+file
            Log.e("TAG","$sn")
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileConversionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(HttpViewModle::class.java)
        viewModel.theResult.observe(this){pdfConvert ->
            binding.btnResult.text = pdfConvert.toString()
        }

        /**
         * 获取文件
         */
        binding.btnTest.setOnClickListener {
            mGetContent.launch("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
        }

        /**
         * 发送请求
         */
        binding.btnUpload.setOnClickListener {
            GlobalScope.launch {
                viewModel.theDemoPOst(file)
            }
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
    suspend fun urlencode(map: Map<*, *>): String {
        val sb = StringBuilder()
        withContext(Dispatchers.IO){
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
        }
        return sb.toString()
    }
}