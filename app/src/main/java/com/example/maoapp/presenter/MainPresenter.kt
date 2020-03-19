package com.example.maoapp.presenter

import com.example.maoapp.base.RxPresenter
import com.example.maoapp.contract.MainContract
import com.example.maoapp.network.helper.RetrofitHelper
import javax.inject.Inject

class MainPresenter  @Inject constructor(private val mRetrofitHelper: RetrofitHelper) :
    RxPresenter<MainContract.View>(), MainContract.Presenter<MainContract.View> {
}