package com.example.maoapp.contract

import com.example.maoapp.base.BaseContract
import com.example.maoapp.model.bean.ShareModelList

interface HomeFragmentContract {

    interface View : BaseContract.BaseView {
        fun setShares(shares:List<ShareModelList.ShareModel>)
        fun showToast(tag:String)
    }
    interface Presenter<in T> : BaseContract.BasePresenter<T> {
        fun getShares()
        fun queryShares(query:String)
        fun getSharesByUserId(userId:Long)
    }
}