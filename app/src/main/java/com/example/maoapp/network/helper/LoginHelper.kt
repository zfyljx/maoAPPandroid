package com.example.maoapp.network.helper

import com.example.maoapp.model.apiBean.LocationBean
import com.example.maoapp.model.apiBean.QiniuToken
import com.example.maoapp.model.apiBean.ResultNoDataBean
import com.example.maoapp.model.apiBean.UserApiBean
import com.example.maoapp.network.api.LoginService
import io.reactivex.Flowable

class LoginHelper(private val mLoginService: LoginService) {
    fun login(phoneNumber:String,password:String): Flowable<UserApiBean> = mLoginService.login(phoneNumber, password)

    fun register(userName:String, phoneNumber:String, password:String):Flowable<UserApiBean> = mLoginService.resterUser(userName, phoneNumber, password)

    fun phoneNumberIsOnly(phoneNumber:String):Flowable<UserApiBean> = mLoginService.phoneNumberIsOnly(phoneNumber)

    fun userNameIsOnly(userName:String):Flowable<UserApiBean> = mLoginService.userNameIsOnly(userName)

    fun getQiniuToken():Flowable<QiniuToken> = mLoginService.getQiniuUpdateToken()

    fun getAdressDetail(longitude:String,latitude:String):Flowable<LocationBean> = mLoginService.getAddressDetail(longitude, latitude)

    fun upLoadShare(id:Long,message:String,address:String,imageOne:String,imageTwo:String,imageThree:String):Flowable<ResultNoDataBean> = mLoginService.saveShare(id, message, address, imageOne, imageTwo, imageThree)
}