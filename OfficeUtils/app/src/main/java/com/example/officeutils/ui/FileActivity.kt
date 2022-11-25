package com.example.officeutils.ui

import android.database.Cursor
import android.os.Bundle
import android.provider.MediaStore
import androidx.lifecycle.ViewModelProvider
import com.example.officeutils.base.BaseActivity
import com.example.officeutils.bean.ImgFolderBean
import com.example.officeutils.databinding.ActivityFileBinding
import com.example.officeutils.utils.FileMangerUtil
import com.example.officeutils.viewmodel.FileViewModel
import java.io.File
import java.io.FilenameFilter


class FileActivity : BaseActivity() {

    lateinit var binding: ActivityFileBinding
    lateinit var viewModel : FileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(FileViewModel::class.java)
        initView()
        initOnclick()
    }

    private fun initView() {
        getLocationFile()
    }

    /**
     * 拿到本地文件
     */
    private fun getLocationFile() {

    }

    private fun initOnclick() {

    }

    /**
     * 得到图片文件夹集合
     */
//    fun getImageFolders(): List<ImgFolderBean>? {
//        val folders: MutableList<ImgFolderBean> = ArrayList<ImgFolderBean>()
//        // 扫描图片
//        var c: Cursor? = null
//        try {
//            c =  this.getContentResolver().query(
//                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
//                null,
//                MediaStore.Images.Media.MIME_TYPE + "= ? or " + MediaStore.Images.Media.MIME_TYPE + "= ?",
//                arrayOf("image/jpeg", "image/png"),
//                MediaStore.Images.Media.DATE_MODIFIED
//            )
//            val mDirs: MutableList<String> = ArrayList() //用于保存已经添加过的文件夹目录
//            while (c.moveToNext()) {
//                val path = c.getString(c.getColumnIndex(MediaStore.Images.Media.DATA)) // 路径
//                val parentFile: File = File(path).getParentFile() ?: continue
//                val dir: String = parentFile.getAbsolutePath()
//                if (mDirs.contains(dir)) //如果已经添加过
//                    continue
//                mDirs.add(dir) //添加到保存目录的集合中
//                val folderBean = ImgFolderBean()
//                folderBean.setDir(dir)
//                folderBean.setFistImgPath(path)
//                if (parentFile.list() == null) continue
//                val count: Int = parentFile.list(object : FilenameFilter() {
//                    fun accept(dir: File?, filename: String): Boolean {
//                        return if (filename.endsWith(".jpeg") || filename.endsWith(".jpg") || filename.endsWith(
//                                ".png"
//                            )
//                        ) {
//                            true
//                        } else false
//                    }
//                }).length
//                folderBean.setCount(count)
//                folders.add(folderBean)
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        } finally {
//            c?.close()
//        }
//        return folders
//    }


}