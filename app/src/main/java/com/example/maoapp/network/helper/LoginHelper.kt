package com.example.maoapp.network.helper

import com.example.maoapp.model.apiBean.*
import com.example.maoapp.network.api.LoginService
import io.reactivex.Flowable

class LoginHelper(private val mLoginService: LoginService) {
    fun login(phoneNumber:String,password:String): Flowable<UserApiBean> = mLoginService.login(phoneNumber, password)

    fun register(userName:String, phoneNumber:String, password:String):Flowable<UserApiBean> = mLoginService.resterUser(userName, phoneNumber, password)

    fun phoneNumberIsOnly(phoneNumber:String):Flowable<UserApiBean> = mLoginService.phoneNumberIsOnly(phoneNumber)

    fun userNameIsOnly(userName:String):Flowable<UserApiBean> = mLoginService.userNameIsOnly(userName)

    fun getQiniuToken():Flowable<QiniuToken> = mLoginService.getQiniuUpdateToken()

    fun getAdressDetail(longitude:String,latitude:String):Flowable<LocationBean> = mLoginService.getAddressDetail(longitude, latitude)

    fun upLoadShare(id:Long,userName: String,message:String,address:String,imageOne:String,imageTwo:String,imageThree:String):Flowable<ResultNoDataBean> = mLoginService.saveShare(id,userName, message, address, imageOne, imageTwo, imageThree)

    fun getShares():Flowable<SharesBean> =mLoginService.getShares()

    fun queryShares(query:String):Flowable<SharesBean> = mLoginService.queryShares(query)

    fun getMineShares(userId:Long):Flowable<SharesBean> = mLoginService.getMineShares(userId)
}