package com.example.maoapp.ui.interfaces

interface IRegisterActivity {

    fun showUserNameResult(result:Boolean)
    fun showSendVerificationResult(result: Boolean)
    fun showRegisterResult(result: Boolean)
    fun checkPhoneNumVerified(result: Boolean)

}