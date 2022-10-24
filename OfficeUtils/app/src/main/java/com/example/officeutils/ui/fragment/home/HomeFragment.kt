package com.example.officeutils.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.officeutils.databinding.FragmentHomeBinding
import java.io.UnsupportedEncodingException
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root


        initView()


        return root
    }

    private fun initView() {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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
    ): String {
        val signStr = "x-date: $datetime\nx-source: $source"
        val mac: Mac = Mac.getInstance("HmacSHA1")
        val sKey: Key = SecretKeySpec(secretKey.toByteArray(charset("UTF-8")), mac.getAlgorithm())
        mac.init(sKey)
        val hash: ByteArray = mac.doFinal(signStr.toByteArray(charset("UTF-8")))
        val sig: String = BASE64Encoder().encode(hash)
        return "hmac id=\"$secretId\", algorithm=\"hmac-sha1\", headers=\"x-date x-source\", signature=\"$sig\""
    }

    @Throws(UnsupportedEncodingException::class)
    fun urlencode(map: Map<*, *>): String {
        val sb = StringBuilder()
        for ((key, value): Map.Entry<*, *> in map) {
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
    @JvmStatic
    fun main(args: Array<String>) {
        //云市场分配的密钥Id
        val secretId = "xxxx"
        //云市场分配的密钥Key
        val secretKey = "xxxx"
        val source = "market"
        val cd: Calendar = Calendar.getInstance()
        val sdf = SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US)
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"))
        val datetime: String = sdf.format(cd.getTime())
        // 签名
        val auth = calcAuthorization(source, secretId, secretKey, datetime)
        // 请求方法
        val method = "POST"
        // 请求头
        val headers: MutableMap<String, String> = HashMap()
        headers["X-Source"] = source
        headers["X-Date"] = datetime
        headers["Authorization"] = auth

        // 查询参数
        val queryParams: Map<String, String> = HashMap()

        // body参数
        val bodyParams: Map<String, String> = HashMap()

        // url参数拼接
        var url = "https://service-1odt5k7b-1255058406.sh.apigw.tencentcs.com/release/v2/jobs"
        if (!queryParams.isEmpty()) {
            url += "?" + urlencode(queryParams)
        }
        var `in`: BufferedReader? = null
        try {
            val realUrl = URL(url)
            val conn: HttpURLConnection = realUrl.openConnection() as HttpURLConnection
            conn.setConnectTimeout(5000)
            conn.setReadTimeout(5000)
            conn.setRequestMethod(method)

            // request headers
            for ((key, value): Map.Entry<String, String> in headers) {
                conn.setRequestProperty(key, value)
            }

            // request body
            val methods: MutableMap<String, Boolean> = HashMap()
            methods["POST"] = true
            methods["PUT"] = true
            methods["PATCH"] = true
            val hasBody = methods[method]
            if (hasBody != null) {
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded")
                conn.setDoOutput(true)
                val out = DataOutputStream(conn.getOutputStream())
                out.writeBytes(urlencode(bodyParams))
                out.flush()
                out.close()
            }

            // 定义 BufferedReader输入流来读取URL的响应
            `in` = BufferedReader(InputStreamReader(conn.getInputStream()))
            var line: String?
            var result: String? = ""
            while (`in`.readLine().also { line = it } != null) {
                result += line
            }
            println(result)
        } catch (e: Exception) {
            println(e)
            e.printStackTrace()
        } finally {
            try {
                if (`in` != null) {
                    `in`.close()
                }
            } catch (e2: Exception) {
                e2.printStackTrace()
            }
        }
    }
}