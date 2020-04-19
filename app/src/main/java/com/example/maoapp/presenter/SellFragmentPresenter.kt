package com.example.maoapp.presenter

import android.util.Log
import com.example.maoapp.base.BaseSubscriber
import com.example.maoapp.base.RxPresenter
import com.example.maoapp.contract.SellFragmentContract
import com.example.maoapp.model.apiBean.SellsBean
import com.example.maoapp.network.helper.LoginHelper
import com.example.maoapp.utils.rxSchedulerHelper
import javax.inject.Inject

class SellFragmentPresenter@Inject constructor(private val mLoginHelper: LoginHelper) :
    RxPresenter<SellFragmentContract.View>(), SellFragmentContract.Presenter<SellFragmentContract.View> {
    override fun getSells() {
        val subscriber =mLoginHelper.getSells()
            .compose(rxSchedulerHelper())
            .subscribeWith(object : BaseSubscriber<SellsBean>(mView) {
                override fun onSuccess(mData: SellsBean) {
                    if (mData.status == 200){
                        Log.d("HHHHHHHHHHHHHH",mData.data.toString())
                        mView?.setSells(mData.data)
                    }else{
                        mView?.showToast("没有新商品")
                    }
                }

//                override fun onError(e: Throwable) {
//                    mView?.showToast("网络出错,请刷新")
//                }
            })
        addSubscribe(subscriber)
    }
}