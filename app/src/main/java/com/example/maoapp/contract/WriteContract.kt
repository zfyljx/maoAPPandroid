package com.example.maoapp.contract

import com.example.maoapp.base.BaseContract

interface WriteContract {

    interface View : BaseContract.BaseView {

        fun setToken(data:String)

        fun setAddressDetail(address:String)

        fun showUploadShareResult(result: Boolean)

        fun showToast(message:String)
    }

    interface Presenter<in T> : BaseContract.BasePresenter<T> {

        fun getQiniuToken()

        fun getAdressDetail(longitude:String,latitude:String)

        fun uploadShare(id:Long,userName:String,message:String,address:String,imageOne:String,imageTwo:String,imageThree:String)

    }
}