package com.example.maoapp.network.api

import com.example.maoapp.model.apiBean.UserApiBean
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface LoginService {

//    @GET("/course3/api/vertical2?api_ver=1.10&keyfrom=course.3.2.2.android&model=MI_6&mid=8.0.0&imei=866822031582307&vendor=xiaomi&screen=1080x1920&abtest=6&Mkt1st=xiaomi&Mkt=xiaomi&Pdt=mCourse.android")
//    fun getVertical(@Query("tag") tag: Int): Flowable<VerticalBean>


    @GET("/getandroidlogin")
    fun login(@Query("phoneNumber")phoneNumber:String, @Query("password")password:String): Flowable<UserApiBean>

    @POST("/postandroidregister")
    fun resterUser(@Query("userName")userName:String,@Query("phoneNumber")phoneNumber:String, @Query("password")password:String):Flowable<UserApiBean>

    @GET("/getandroidphonenumberisonly")
    fun phoneNumberIsOnly(@Query("phoneNumber")phoneNumber:String):Flowable<UserApiBean>

    @GET("getandroidusernameisonly")
    fun userNameIsOnly(@Query("userName")userName:String):Flowable<UserApiBean>
}