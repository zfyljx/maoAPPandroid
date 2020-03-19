package com.example.maoapp.presenter

import com.example.maoapp.base.RxPresenter
import com.example.maoapp.contract.RegisterContract
import com.example.maoapp.network.helper.RetrofitHelper
import javax.inject.Inject

class RegisterPresenter @Inject constructor(private val mRetrofitHelper: RetrofitHelper) :
    RxPresenter<RegisterContract.View>(), RegisterContract.Presenter<RegisterContract.View> {
}