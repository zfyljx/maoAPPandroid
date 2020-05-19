package com.example.maoapp.contract

import com.example.maoapp.base.BaseContract
import com.example.maoapp.model.bean.SellModel

interface SellFragmentContract {

    interface View : BaseContract.BaseView {
        fun setSells(sells:List<SellModel>)
        fun showToast(tag:String)
        fun setQuerySells(sells:List<SellModel>)
    }
    interface Presenter<in T> : BaseContract.BasePresenter<T> {
        fun getSells()
        fun querySells(query:String)
    }
}