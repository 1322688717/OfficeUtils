package com.example.officeutils.ui

import android.content.ActivityNotFoundException
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import com.example.officeutils.databinding.ActivityFileConversionBinding
import com.example.officeutils.utils.Base64Encoder
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
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


class FileConversionActivity : AppCompatActivity() {

    lateinit var binding: ActivityFileConversionBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileConversionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initData()

        binding.btnTest.setOnClickListener {
            //RequestResponse.JYTService.getPDFToWord()
           // FileUtil.getInstance().openFile(this,"sdcard")
//            val intent = Intent(Intent.ACTION_PICK, null)
//            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "application/pdf")
//            startActivityForResult(intent, 1)


            val intent =  Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("application/pdf");
            try {
                startActivityForResult(intent, 1);
            } catch (e : ActivityNotFoundException) {
                //alert user that file manager not working
               // ToastUtils.ToastShort(Utils.getContext().getResources().getString(R.string.toast_pick_file_error));
            }

        }
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
        queryParams["document_url"] = ""
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
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true).build()


        okHttpClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {

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