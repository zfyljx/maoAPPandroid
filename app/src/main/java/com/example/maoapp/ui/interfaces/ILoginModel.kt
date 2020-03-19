package com.example.maoapp.ui.interfaces

interface ILoginModel {
    fun resquestLogin(phoneNumber:String,password:String)
    fun requestCheckPhoneVerfied(phoneNumber: String)
}