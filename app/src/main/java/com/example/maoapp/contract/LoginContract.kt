package com.example.maoapp.contract

import com.example.maoapp.base.BaseContract
import com.example.maoapp.model.apiBean.UserApiBean

interface LoginContract {
    interface View : BaseContract.BaseView {

//        fun showDiscoveryBean(mDiscoveryCommentBean: DiscoveryCommentBean)

//        fun showSetTag(tagSuccessBean: TagSuccessBean)
        fun showToast(tag:UserApiBean)
        fun navigateToMain()
    }

    interface Presenter<in T> : BaseContract.BasePresenter<T> {

        /**
         * @param tagId [所处阶段Id,阶段对应级别（如初中->初一、初二、初三）]
         * @return
         */
//        fun getRegionTagTypeBean(tagId: ArrayList<Int>)

        fun getLogin(phoneNumber:String,password:String)
    }
}