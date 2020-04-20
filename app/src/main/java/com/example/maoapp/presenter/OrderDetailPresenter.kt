package com.example.maoapp.presenter

import com.example.maoapp.base.BaseSubscriber
import com.example.maoapp.base.RxPresenter
import com.example.maoapp.contract.OrderDetailContract
import com.example.maoapp.model.apiBean.OrderBean
import com.example.maoapp.model.apiBean.ResultNoDataBean
import com.example.maoapp.network.helper.LoginHelper
import com.example.maoapp.utils.rxSchedulerHelper
import javax.inject.Inject

class OrderDetailPresenter@Inject constructor(private val mLoginHelper: LoginHelper) :
    RxPresenter<OrderDetailContract.View>(), OrderDetailContract.Presenter<OrderDetailContract.View> {
    /**
     * @param tagId [所处阶段Id,阶段对应级别（如初中->初一、初二、初三）]
     * @return
     */
    override fun getOrder(orderId: Long) {
        val subscriber = mLoginHelper.getOrder(orderId)
            .compose(rxSchedulerHelper())
            .subscribeWith(object : BaseSubscriber<OrderBean>(mView) {
                override fun onSuccess(mData: OrderBean) {
                    if (mData.status == 200){
                        mView?.setOrder(mData.data)
                    }else{
                        mView?.showToast("网络出错")
                    }

                }
            })
        addSubscribe(subscriber)
    }

    /**
     * @param tagId [所处阶段Id,阶段对应级别（如初中->初一、初二、初三）]
     * @return
     */
    override fun updateStatus(orderId: Long) {
        val subscriber = mLoginHelper.updateOrder(orderId)
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
