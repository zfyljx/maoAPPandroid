package com.example.maoapp.presenter

import com.example.maoapp.base.BaseSubscriber
import com.example.maoapp.base.RxPresenter
import com.example.maoapp.contract.LoginContract
import com.example.maoapp.model.apiBean.UserApiBean
import com.example.maoapp.network.helper.LoginHelper
import com.example.maoapp.utils.rxSchedulerHelper
import javax.inject.Inject

//class LoginPresenter(private var mLoginActivity: ILoginActivity) {
//
////    private
////    fun requestCheckPhoneIsVerified(phoneNumber:String){
////        mLoginModel.requestCheckPhoneVerfied(phoneNumber)
////    }
////
////     fun requestlogin(phoneNumber: String,password:String){
////        mLoginModel.resquestLogin(phoneNumber,password)
////    }
//
//    fun showPhoneNumberVerifiedResult(result:Boolean){
//        mLoginActivity.getLoginPhoneNumIsVerified(result)
//
//    }
//
//    fun showLoginResult(result: Boolean){
//        mLoginActivity.getLoginResult(result)
//    }
//}
class LoginPresenter@Inject constructor(private val mLoginHelper: LoginHelper) :
    RxPresenter<LoginContract.View>(), LoginContract.Presenter<LoginContract.View> {
    /**
     * @param tagId [所处阶段Id,阶段对应级别（如初中->初一、初二、初三）]
     * @return
     */
    override fun getLogin(phoneNumber: String, password: String) {
        val subscriber = mLoginHelper.login(phoneNumber,password)
            .compose(rxSchedulerHelper())
            .subscribeWith(object : BaseSubscriber<UserApiBean>(mView) {
                override fun onSuccess(mData: UserApiBean) {
                    mView?.showToast(mData)
                }
            })
        addSubscribe(subscriber)
    }


   private fun observeStatus(data:UserApiBean){
       if (data.status == 200){

       }
   }
}