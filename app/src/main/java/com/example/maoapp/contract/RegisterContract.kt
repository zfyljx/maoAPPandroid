package com.example.maoapp.contract

import com.example.maoapp.base.BaseContract
import com.example.maoapp.model.apiBean.UserApiBean

interface RegisterContract {
    interface View : BaseContract.BaseView {

//        fun showDiscoveryBean(mDiscoveryCommentBean: DiscoveryCommentBean)

        fun showSetTag(tagSuccessBean: UserApiBean)
        fun showToast(meg:String)
        fun navigateToMain()
        fun showSendVerificationResult(result: Boolean)
        fun showCodeVerificationResult(result: Boolean)
        fun showUserNameIsOnly(result: Boolean)
        fun showPhoneNumberIsOnly(result: Boolean)
        fun showCodeResult(result: Boolean)
    }

    interface Presenter<in T> : BaseContract.BasePresenter<T> {

        /**
         * @param tagId [所处阶段Id,阶段对应级别（如初中->初一、初二、初三）]
         * @return
         */
//        fun getRegionTagTypeBean(tagId: ArrayList<Int>)
        fun mobilePhoneNumberVerification(phoneNumber:String)
        fun registerUser(userName:String, phoneNumber:String, password:String,code:String)
        fun phoneNumberIsOnly(phoneNumber: String,code: String)
        fun userNameIsOnly(userName: String)
//        fun verifyCode(code:String,phoneNumber: String)

    }
}