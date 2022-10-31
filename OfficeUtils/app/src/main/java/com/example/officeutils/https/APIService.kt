package com.example.officeutils.https


import com.example.officeutils.bean.BeanDPFWord
import com.example.officeutils.bean.BeanLogin
import com.example.officeutils.bean.BeanRegister
import com.example.officeutils.bean.RequestRegiseter
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface APIService {
    @GET("/release/convert")
    fun getPDFToWord(@Query("type") type : String) : Call<BeanDPFWord>

    @POST("/androidServer/register")
    fun register(@Body rr : RequestRegiseter) : Call<BeanRegister>

    @FormUrlEncoded
    @POST("/androidServer/login")
    fun login(@Field("username") phone : String ,@Field("password") password : String) :  Call<BeanLogin>
//    @GET("/api/sao")
//    fun getSao(@Query("type") type : String) : Call<SaoBean>
//
//    @GET("/api/rand.qinghua")
//    fun getErathy(@Query("format") format : String) : Call<ErathyBean>
//
//    @GET("/v3/weather/now.json?key=SRSE70OFAul-Ppk2W")
//    fun getNowWeather(@Query("location") location : String ) : Call<BeanNowWeather>
//
//    @GET("/v3/weather/daily.json?key=SRSE70OFAul-Ppk2W")
//    fun getForecastWeather(@Query("location") location : String ) : Call<BeanForecastWeather>
//
//    @GET("/v3/life/suggestion.json?key=SRSE70OFAul-Ppk2W")
//    fun getLifeIndex(@Query("location") location : String ) : Call<BeanForecastWeather>
//
//    @GET("captchaImage")
//    fun getCode() : Call<BeanCode>
//
//    /**
//     * 这里是以表单形式post请求
//     */
////    @FormUrlEncoded()
////    @POST("/prod-api/login")
////    fun getLogin(@Field("code") code : String, @Field("password") password : String, @Field("username") username : String, @Field("uuid") uuid : String  ) : Call<BeanLogin>
//
//    /**
//     * 这里是以json形式请求
//     */
//    @POST("/login")
//    fun getLogin(@Body body: BeanGologin): Call<BeanLogin>
//
//    @GET("/system/user/profile")
//    fun getUserInfo(@Header("Authorization")  token : String) : Call<Userinfo>
//
//    @Headers("Authorization: Bearer eyJhbGciOiJIUzUxMiJ9.eyJsb2dpbl91c2VyX2tleSI6IjIyYjQwOWYyLTkzOGQtNDg5Yi04ZTYyLWMxMDJiMDlkYWViNCJ9.0xj2RUg9-7cDKTmYUeSbbZxTvBmYDrGZeccj9GEbNzs9ALoG1_KlAdjURciT6Q3GscyIVS8f1F0j6NVgVwnHQQ")
//    @GET("/logout")
//    fun logout() : Call<BeanLogout>
}

//internal interface Api {
//    /**
//     * 使用@Body注解作为参数
//     */
//    @POST("/user/updateUserInfo")
//    fun updateUserInfo3(@Body body: RequestBody?): Observable<BaseModel?>?
//
//    /**
//     * 使用@Field注解作为参数
//     */
//    @FormUrlEncoded //@Field参数 一定需要这个注解
//    @POST("/user/updateUserInfo")
//    fun updateUserInfo1(
//        @Field("phone") phone: String?,
//        @Field("isUpdated") isUpdated: String?
//    ): Observable<BaseModel?>?
//
//    /**
//     * 使用@FieldMap注解作为参数
//     */
//    @POST("/user/updateUserInfo")
//    fun updateUserInfo2(@FieldMap param: Map<*, *>?): Observable<BaseModel?>?
//}
