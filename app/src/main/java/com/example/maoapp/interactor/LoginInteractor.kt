package com.example.maoapp.interactor

import com.example.maoapp.presenter.LoginPresenter
import com.example.maoapp.ui.interfaces.ILoginActivity
import com.example.maoapp.ui.interfaces.ILoginInteractor

class LoginInteractor( mLoginActivity: ILoginActivity):ILoginInteractor {

    private val presenter=LoginPresenter(mLoginActivity)
    override fun resquestLogin(phoneNumber: String, password: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun requestCheckPhoneVerfied(phoneNumber: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}