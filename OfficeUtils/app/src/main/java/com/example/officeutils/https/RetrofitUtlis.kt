package com.example.officeutils.https

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * 简易接口
 * 调用地址：https://service-1odt5k7b-1255058406.sh.apigw.tencentcs.com/release/convert
 * 请求方式：GET
 * 返回类型：JSON
 */

/**
 * 高级接口
 * 调用地址：https://service-1odt5k7b-1255058406.sh.apigw.tencentcs.com/release/v2/jobs
 * 请求方式：POST
 * 返回类型：JSON
 */

class RetrofitUtlis {
    private var Base_URL = ""

    fun getOKHttp() : OkHttpClient {
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true).build()
        return client
    }

    fun getRetrofit(type: Int) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(getUrl(type))
            .client(getOKHttp())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * 调用create方法，传入service接口对应的Class类型，创建一个该接口的动态代理对象
     *
     * 动态代理：有了这个动态代理对象后，我们就可以随意调用接口中定义的所有方法
     */
    public fun <T> create(serviceClass: Class<T>,type: Int) : T =  getRetrofit(type).create(serviceClass)

    fun getUrl(type : Int) : String{
        when (type){
            1 -> Base_URL = "https://service-1odt5k7b-1255058406.sh.apigw.tencentcs.com"  //韩小韩API接口站
            2 -> Base_URL = "https://api.uomg.com"  //UomgAPI 接口网站
            3 -> Base_URL = "https://api.seniverse.com/"  //心知天气
            4 -> Base_URL = "http://1.12.240.180:8083/androidServer/"  //胡总服务器
        }
        return Base_URL
    }
}