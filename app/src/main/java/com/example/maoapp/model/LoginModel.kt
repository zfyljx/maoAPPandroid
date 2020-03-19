package com.example.maoapp.model

import com.example.maoapp.ui.interfaces.ILoginModel

class LoginModel:ILoginModel {
    private lateinit var  phoneNumList:List<String>
    override fun resquestLogin(phoneNumber: String, password: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun requestCheckPhoneVerfied(phoneNumber: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}