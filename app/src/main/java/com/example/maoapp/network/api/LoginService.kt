package com.example.maoapp.network.api

import com.example.maoapp.model.apiBean.*
import io.reactivex.Flowable
import retrofit2.http.*

interface LoginService {

//    @GET("/course3/api/vertical2?api_ver=1.10&keyfrom=course.3.2.2.android&model=MI_6&mid=8.0.0&imei=866822031582307&vendor=xiaomi&screen=1080x1920&abtest=6&Mkt1st=xiaomi&Mkt=xiaomi&Pdt=mCourse.android")
//    fun getVertical(@Query("tag") tag: Int): Flowable<VerticalBean>


    @GET("/getandroidlogin")
    fun login(@Query("phoneNumber")phoneNumber:String, @Query("password")password:String): Flowable<UserApiBean>

    @FormUrlEncoded
    @POST("/postandroidregister")
    fun resterUser(@Field("userName")userName:String, @Field("phoneNumber")phoneNumber:String, @Field("password")password:String):Flowable<UserApiBean>

    @GET("/getandroidphonenumberisonly")
    fun phoneNumberIsOnly(@Query("phoneNumber")phoneNumber:String):Flowable<UserApiBean>

    @GET("/getandroidusernameisonly")
    fun userNameIsOnly(@Query("userName")userName:String):Flowable<UserApiBean>

    @GET("/androidgettoken")
    fun getQiniuUpdateToken():Flowable<QiniuToken>
    
    @GET("/getandroidaddressdetail")
    fun getAddressDetail(@Query("longitude")longitude:String,@Query("latitude")latitude:String):Flowable<LocationBean>

    @FormUrlEncoded
    @POST("/postandroidsaveshare")
    fun saveShare(@Field("id")id:Long,@Field("userName")userName: String,@Field("message")message:String,@Field("address")address:String,@Field("imageOne")imageOne:String,@Field("imageTwo")imageTwo:String,@Field("imageThree")imageThree:String):Flowable<ResultNoDataBean>

    @GET("/getandroidshares")
    fun getShares():Flowable<SharesBean>

    @GET("/getandroidqueryshares")
    fun queryShares(@Query("query")query:String):Flowable<SharesBean>

    @GET("/getandroidmineshares")
    fun getMineShares(@Query("userId")userId:Long):Flowable<SharesBean>

    @GET("/getandroidallsells")
    fun getSells():Flowable<SellsBean>

    @GET("/getandroidsell")
    fun getSellById(@Query("id")id:Long):Flowable<SellBean>

    @GET("/getandroidsellsbyuserid")
    fun getSellsByUserId(@Query("userId")userId:Long):Flowable<SellsBean>

    @FormUrlEncoded
    @POST("/postandroidbuildorder")
    fun buildOrder(@Field("goodId")goodId:Long,@Field("storeId")storeId:Long,@Field("storeName")storeName:String,@Field("name")name:String,@Field("userId")userId:Long,@Field("userName")userName:String,@Field("userPhone")userPhone:String,@Field("userAddress")userAddress:String,@Field("imagePath")imagePath:String,@Field("price")price:Float,@Field("number")number:Int,@Field("totalPrice")totalPrice:Float):Flowable<ResultNoDataBean>
}