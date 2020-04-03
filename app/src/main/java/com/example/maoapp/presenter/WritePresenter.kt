package com.example.maoapp.presenter

import com.example.maoapp.base.BaseSubscriber
import com.example.maoapp.base.RxPresenter
import com.example.maoapp.contract.WriteContract
import com.example.maoapp.model.apiBean.QiniuToken
import com.example.maoapp.network.helper.LoginHelper
import com.example.maoapp.utils.rxSchedulerHelper
import javax.inject.Inject

class WritePresenter@Inject constructor(private val mLoginHelper: LoginHelper) :
    RxPresenter<WriteContract.View>(), WriteContract.Presenter<WriteContract.View>  {
    override fun getQiniuToken() {
        val subscriber = mLoginHelper.getQiniuToken()
            .compose(rxSchedulerHelper())
            .subscribeWith(object : BaseSubscriber<QiniuToken>(mView) {
                override fun onSuccess(mData: QiniuToken) {
                   if (mData.status == 200){
                       mView?.setToken(mData.data.token)
                   }
                }
            })
        addSubscribe(subscriber)
    }
}