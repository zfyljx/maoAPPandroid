package com.example.maoapp.contract

import com.example.maoapp.base.BaseContract
import com.example.maoapp.model.bean.ShareModelList

interface MineShareContract {

    interface View : BaseContract.BaseView {
        fun setShares(shares: List<ShareModelList.ShareModel>)
        fun showToast(tag:String)
        fun verityStatus(result:Boolean)
    }
    interface Presenter<in T> : BaseContract.BasePresenter<T> {
        fun getShares(userId:Long)
        fun updateStatus(id:Long)
    }
}