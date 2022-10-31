package com.example.officeutils.ui

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import com.example.officeutils.databinding.ActivityFileConversionBinding
import com.example.officeutils.utils.FileToBase64
import com.example.officeutils.viewmodel.HttpViewModle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class FileConversionActivity : AppCompatActivity() {
    val TAG = "FileConversionActivity"
    lateinit var binding: ActivityFileConversionBinding
    lateinit var viewModel: HttpViewModle
    val context = this

    var file : String = ""
    lateinit var uri : Uri

    //对文件管理器返回值做处理
    @RequiresApi(Build.VERSION_CODES.N)
    var mGetContent = registerForActivityResult<String, Uri>(ActivityResultContracts.GetContent()) { uri1 ->
        // Handle the returned Uri
        uri = uri1
        Log.i(TAG, "onActivityResult:$uri")
        GlobalScope.launch {
            viewModel.getFile(this@FileConversionActivity,uri)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileConversionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(HttpViewModle::class.java)

        viewModel.baseToFile.observe(this){ base64andname ->
            binding.btnResult.text = base64andname.data?.id.toString().substring(6)
        }

        /**
         * 获取文件
         */
        binding.btnTest.setOnClickListener {
            mGetContent.launch("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
        }

        /**
         * 发送请求
         * 注意最好二选一
         */
        binding.btnUpload.setOnClickListener {
            viewModel.pliceParameters()
            GlobalScope.launch {
                //okhttp  请求
                //viewModel.postPdfConvert()
                // 原生请求
                viewModel.file.value?.let { it1 -> viewModel.theDemoPOst(it1,context) }
            }
        }

        /**
         * 创建文件
         */
        binding.createFile.setOnClickListener{
            viewModel.baseToFile.observe(this){ the->
                checkPermission()
                GlobalScope.launch {
                    val mysely = the.data?.tasks?.get(0)?.urlArray.toString()
                    val sely_2 = mysely.subSequence(IntRange(80,mysely.length-2)).toString()
                    savaPdf(sely_2
                        ,the.data?.id.toString().subSequence(3,5).toString())
                }
            }

        }

    }

    // Request code for creating a PDF document.
    val CREATE_FILE = 1

    private fun createFile(pickerInitialUri: Uri) {
        val intent = Intent(Intent.ACTION_CREATE_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
            putExtra(Intent.EXTRA_TITLE, "invoice.pdf")

            // Optionally, specify a URI for the directory that should be opened in
            // the system file picker before your app creates the document.
            //默认跳出指定文件目录
            //putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri)
        }
        startActivityForResult(intent, CREATE_FILE)
    }


    /**
     * 保存文件
     */
    suspend fun savaPdf(base64:String,name:String){
        withContext(Dispatchers.IO){
            Log.i(TAG, "savaPdf: $name")
            FileToBase64.saveFileName(base64, "$name.pdf")
        }
    }

    private val REQUEST_EXTERNAL_STORAGE = 1
    private val PERMISSIONS_STORAGE = arrayOf(
        "android.permission.READ_EXTERNAL_STORAGE",
        "android.permission.WRITE_EXTERNAL_STORAGE"
    )

    private fun checkPermission() {
//检查权限（NEED_PERMISSION）是否被授权 PackageManager.PERMISSION_GRANTED表示同意授权
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
            ) {
                Toast.makeText(this, "请开通相关权限，否则无法正常使用本应用！", Toast.LENGTH_SHORT).show()
            }
            //申请权限
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE)
        } else {
            Log.i(TAG, "checkPermission: 成功授权")
        }
    }


}