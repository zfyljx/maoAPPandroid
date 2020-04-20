package com.example.maoapp.presenter

import android.util.Log
import com.example.maoapp.base.BaseSubscriber
import com.example.maoapp.base.RxPresenter
import com.example.maoapp.contract.MineOrderFragmentContract
import com.example.maoapp.model.apiBean.OrdersBean
import com.example.maoapp.model.apiBean.ResultNoDataBean
import com.example.maoapp.network.helper.LoginHelper
import com.example.maoapp.utils.rxSchedulerHelper
import javax.inject.Inject

class MineOrderFragmentPresenter@Inject constructor(private val mLoginHelper: LoginHelper) :
    RxPresenter<MineOrderFragmentContract.View>(), MineOrderFragmentContract.Presenter<MineOrderFragmentContract.View> {
    override fun getOrders(userId: Long) {
        val subscriber =mLoginHelper.getMyOrders(userId)
            .compose(rxSchedulerHelper())
            .subscribeWith(object : BaseSubscriber<OrdersBean>(mView) {
                override fun onSuccess(mData: OrdersBean) {
                    if (mData.status == 200){
                        Log.d("HHHHHHHHHHHHHH",mData.data.toString())
                        mView?.setOrders(mData.data)
                    }else{
                        mView?.showToast("没有新订单")
                    }
                }

//                override fun onError(e: Throwable) {
//                    mView?.showToast("网络出错,请刷新")
//                }
            })
        addSubscribe(subscriber)
    }

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