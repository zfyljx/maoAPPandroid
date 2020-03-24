package com.example.maoapp.network.helper

import com.example.maoapp.model.apiBean.UserApiBean
import com.example.maoapp.network.api.LoginService
import io.reactivex.Flowable

class LoginHelper(private val mLoginService: LoginService) {
    fun login(phoneNumber:String,password:String): Flowable<UserApiBean> = mLoginService.login(phoneNumber, password)

    fun register(userName:String, phoneNumber:String, password:String):Flowable<UserApiBean> = mLoginService.resterUser(userName, phoneNumber, password)

    fun phoneNumberIsOnly(phoneNumber:String):Flowable<UserApiBean> = mLoginService.phoneNumberIsOnly(phoneNumber)

    fun userNameIsOnly(userName:String):Flowable<UserApiBean> = mLoginService.userNameIsOnly(userName)
}