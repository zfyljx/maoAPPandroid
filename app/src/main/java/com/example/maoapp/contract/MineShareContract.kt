package com.example.maoapp.contract

import com.example.maoapp.base.BaseContract
import com.example.maoapp.model.bean.ShareModelList

interface MineShareContract {

    interface View : BaseContract.BaseView {
        fun setShares(shares: ShareModelList)
        fun showToast(tag:String)
    }
    interface Presenter<in T> : BaseContract.BasePresenter<T> {
        fun getShares(userId:Long)
    }
}