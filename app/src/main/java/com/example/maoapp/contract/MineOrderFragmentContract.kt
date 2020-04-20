package com.example.maoapp.contract

import com.example.maoapp.base.BaseContract
import com.example.maoapp.model.bean.OrderModel

interface MineOrderFragmentContract {

    interface View : BaseContract.BaseView {
        fun setOrders(orders:List<OrderModel>)
        fun showToast(tag:String)
        fun verityStatus(result:Boolean)
    }
    interface Presenter<in T> : BaseContract.BasePresenter<T> {
        fun getOrders(userId:Long)

        fun updateStatus(orderId: Long)

    }
}