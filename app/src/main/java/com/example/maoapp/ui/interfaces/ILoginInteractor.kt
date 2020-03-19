package com.example.maoapp.ui.interfaces

interface ILoginInteractor {
    fun resquestLogin(phoneNumber:String,password:String)
    fun requestCheckPhoneVerfied(phoneNumber: String)
}