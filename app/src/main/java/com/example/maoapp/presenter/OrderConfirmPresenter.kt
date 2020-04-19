package com.example.maoapp.presenter

import com.example.maoapp.base.BaseSubscriber
import com.example.maoapp.base.RxPresenter
import com.example.maoapp.contract.OrderConfirmContract
import com.example.maoapp.model.apiBean.ResultNoDataBean
import com.example.maoapp.model.apiBean.SellBean
import com.example.maoapp.network.helper.LoginHelper
import com.example.maoapp.utils.rxSchedulerHelper
import javax.inject.Inject

class OrderConfirmPresenter@Inject constructor(private val mLoginHelper: LoginHelper) :
    RxPresenter<OrderConfirmContract.View>(), OrderConfirmContract.Presenter<OrderConfirmContract.View> {
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

    override fun buildOrder(
        goodId:Long,
        storeId: Long,
        storeName: String,
        name: String,
        userId: Long,
        userName: String,
        userPhone: String,
        userAddress: String,
        imagePath: String,
        price: Float,
        number: Int,
        totalPrice: Float
    ) {
        val subscriber = mLoginHelper.buildOrder(goodId,storeId, storeName, name, userId, userName, userPhone, userAddress, imagePath, price, number, totalPrice)
            .compose(rxSchedulerHelper())
            .subscribeWith(object : BaseSubscriber<ResultNoDataBean>(mView) {
                override fun onSuccess(mData: ResultNoDataBean) {
                    if (mData.status == 200){
                        mView?.verityPay(true)
                    }else{
                        mView?.verityPay(false)
                    }

                }
            })
        addSubscribe(subscriber)
    }
}