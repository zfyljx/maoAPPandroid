package com.example.maoapp.presenter

import com.example.maoapp.base.RxPresenter
import com.example.maoapp.contract.WriteContract
import com.example.maoapp.network.helper.LoginHelper
import javax.inject.Inject

class WritePresenter@Inject constructor(private val mLoginHelper: LoginHelper) :
    RxPresenter<WriteContract.View>(), WriteContract.Presenter<WriteContract.View>  {
}