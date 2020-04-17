package com.example.maoapp.presenter

import com.example.maoapp.base.BaseSubscriber
import com.example.maoapp.base.RxPresenter
import com.example.maoapp.contract.MineShareContract
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
                        mView?.showToast("网络出错")
                    }
                }

                override fun onError(e: Throwable) {
                    mView?.showToast("网络出错,请刷新")
                }
            })
        addSubscribe(subscriber)
    }
}