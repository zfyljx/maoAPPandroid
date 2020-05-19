package com.example.maoapp.presenter

import android.util.Log
import com.example.maoapp.base.BaseSubscriber
import com.example.maoapp.base.RxPresenter
import com.example.maoapp.contract.HomeFragmentContract
import com.example.maoapp.model.apiBean.SharesBean
import com.example.maoapp.network.helper.LoginHelper
import com.example.maoapp.utils.rxSchedulerHelper
import javax.inject.Inject

class HomeFragmentPresenter@Inject constructor(private val mLoginHelper: LoginHelper) :
    RxPresenter<HomeFragmentContract.View>(), HomeFragmentContract.Presenter<HomeFragmentContract.View> {
    override fun getShares() {
        val subscriber =mLoginHelper.getShares()
            .compose(rxSchedulerHelper())
            .subscribeWith(object : BaseSubscriber<SharesBean>(mView) {
                override fun onSuccess(mData: SharesBean) {
                    if (mData.status == 200){
                        Log.d("HHHHHHHHHHHHHH",mData.data.toString())
                        mView?.setShares(mData.data)
                    }else{
                        mView?.showToast("没有新分享")
                    }
                }

//                override fun onError(e: Throwable) {
//                    mView?.showToast("网络出错,请刷新")
//                }
            })
        addSubscribe(subscriber)
    }

    override fun queryShares(query: String) {
        val subscriber =mLoginHelper.queryShares(query)
            .compose(rxSchedulerHelper())
            .subscribeWith(object : BaseSubscriber<SharesBean>(mView) {
                override fun onSuccess(mData: SharesBean) {
                    if (mData.status == 200){
                        mView?.setShares(mData.data)
                    }else{
                        mView?.showToast("网络出错")
                    }
                }

                override fun onError(e: Throwable) {
                    mView?.showToast("网络出错,请刷新")
                }
            })
        addSubscribe(subscriber)
    }

    override fun getSharesByUserId(userId: Long) {
        val subscriber =mLoginHelper.getSharesByUserId(userId)
            .compose(rxSchedulerHelper())
            .subscribeWith(object : BaseSubscriber<SharesBean>(mView) {
                override fun onSuccess(mData: SharesBean) {
                    if (mData.status == 200){
                        Log.d("HHHHHHHHHHHHHH",mData.data.toString())
                        mView?.setShares(mData.data)
                    }else{
                        mView?.showToast("网络出错")
                    }
                }

//                override fun onError(e: Throwable) {
//                    mView?.showToast("网络出错,请刷新")
//                }
            })
        addSubscribe(subscriber)
    }
}