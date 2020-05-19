package com.example.maoapp.presenter

import com.example.maoapp.base.BaseSubscriber
import com.example.maoapp.base.RxPresenter
import com.example.maoapp.contract.MineShareContract
import com.example.maoapp.model.apiBean.ResultNoDataBean
import com.example.maoapp.model.apiBean.SharesBean
import com.example.maoapp.network.helper.LoginHelper
import com.example.maoapp.utils.rxSchedulerHelper
import javax.inject.Inject

class MineSharesPresenter@Inject constructor(private val mLoginHelper: LoginHelper) :
    RxPresenter<MineShareContract.View>(), MineShareContract.Presenter<MineShareContract.View> {
    override fun getShares(userId:Long) {
        val subscriber =mLoginHelper.getMineShares(userId)
            .compose(rxSchedulerHelper())
            .subscribeWith(object : BaseSubscriber<SharesBean>(mView) {
                override fun onSuccess(mData: SharesBean) {
                    if (mData.status == 200){
                        mView?.setShares(mData.data)
                    }else{
                        mView?.showToast("没有新分享")
                    }
                }

                override fun onError(e: Throwable) {
                    mView?.showToast("网络出错,请刷新")
                }
            })
        addSubscribe(subscriber)
    }

    override fun updateStatus(id: Long) {
        val subscriber = mLoginHelper.updateSharesStatus(id)
            .compose(rxSchedulerHelper())
            .subscribeWith(object : BaseSubscriber<ResultNoDataBean>(mView) {
                override fun onSuccess(mData: ResultNoDataBean) {
                    if (mData.status == 200){
                        mView?.verityStatus(true)
                    }else{
                        mView?.verityStatus(false)
                    }

                }
            })
        addSubscribe(subscriber)
    }
}