package com.example.maoapp.contract

import com.example.maoapp.base.BaseContract
import com.example.maoapp.model.bean.SellModel

interface OrderConfirmContract {

    interface View : BaseContract.BaseView {

        fun showTost(tag:String)

        fun setCommodity(sell: SellModel)

        fun verityPay(result:Boolean)
    }

    interface Presenter<in T> : BaseContract.BasePresenter<T> {

        fun getSellById(id:Long)

        fun buildOrder(storeId:Long,storeName:Long,name:String,userId:Long,userName:String,userPhone:String,userAddress:String,imagePath:String,price:Float,number:Int,totalPrice:Float)

    }
}