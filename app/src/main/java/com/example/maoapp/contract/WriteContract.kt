package com.example.maoapp.contract

import com.example.maoapp.base.BaseContract

interface WriteContract {

    interface View : BaseContract.BaseView {

    }

    interface Presenter<in T> : BaseContract.BasePresenter<T> {


    }
}