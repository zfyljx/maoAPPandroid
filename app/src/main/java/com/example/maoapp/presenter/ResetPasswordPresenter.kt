package com.example.maoapp.presenter

import com.example.maoapp.base.RxPresenter
import com.example.maoapp.contract.ResetPasswordContract
import com.example.maoapp.network.helper.RetrofitHelper
import javax.inject.Inject

class ResetPasswordPresenter @Inject constructor(private val mRetrofitHelper: RetrofitHelper) :
    RxPresenter<ResetPasswordContract.View>(), ResetPasswordContract.Presenter<ResetPasswordContract.View>
{
}