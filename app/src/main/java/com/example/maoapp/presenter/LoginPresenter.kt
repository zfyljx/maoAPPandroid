package com.example.maoapp.presenter

import com.example.maoapp.ui.activity.LoginActivity

class LoginPresenter(private var mLoginActivity: ILoginActivity) {

//    private
//    fun requestCheckPhoneIsVerified(phoneNumber:String){
//        mLoginModel.requestCheckPhoneVerfied(phoneNumber)
//    }
//
//     fun requestlogin(phoneNumber: String,password:String){
//        mLoginModel.resquestLogin(phoneNumber,password)
//    }

    fun showPhoneNumberVerifiedResult(result:Boolean){
        mLoginActivity.getLoginPhoneNumIsVerified(result)

    }

    fun showLoginResult(result: Boolean){
        mLoginActivity.getLoginResult(result)
    }
}