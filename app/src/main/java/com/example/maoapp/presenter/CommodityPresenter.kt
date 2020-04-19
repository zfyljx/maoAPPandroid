package com.example.maoapp.presenter

import com.example.maoapp.base.BaseSubscriber
import com.example.maoapp.base.RxPresenter
import com.example.maoapp.contract.CommodityContract
import com.example.maoapp.model.apiBean.SellBean
import com.example.maoapp.network.helper.LoginHelper
import com.example.maoapp.utils.rxSchedulerHelper
import javax.inject.Inject

class CommodityPresenter@Inject constructor(private val mLoginHelper: LoginHelper) :
    RxPresenter<CommodityContract.View>(), CommodityContract.Presenter<CommodityContract.View> {
    override fun getSellById(id: Long) {
        val subscriber = mLoginHelper.getSellById(id)
            .compose(rxSchedulerHelper())
            .subscribeWith(object : BaseSubscriber<SellBean>(mView) {
                override fun onSuccess(mData: SellBean) {
                    if (mData.status == 200){
                        mView?.setCommodity(mData.data)
                    }else{
                        mView?.showTost("网络出错，请刷新")
                    }

                }
            })
        addSubscribe(subscriber)
    }
}