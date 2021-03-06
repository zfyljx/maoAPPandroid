package com.example.maoapp.presenter

import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import cn.smssdk.EventHandler
import cn.smssdk.SMSSDK
import com.example.maoapp.base.BaseSubscriber
import com.example.maoapp.base.RxPresenter
import com.example.maoapp.contract.RegisterContract
import com.example.maoapp.model.apiBean.UserApiBean
import com.example.maoapp.network.helper.LoginHelper
import com.example.maoapp.utils.rxSchedulerHelper
import com.mob.MobSDK
import javax.inject.Inject

class RegisterPresenter @Inject constructor(private val mLoginHelper: LoginHelper) :
    RxPresenter<RegisterContract.View>(), RegisterContract.Presenter<RegisterContract.View> {
    /**
     * @param tagId [所处阶段Id,阶段对应级别（如初中->初一、初二、初三）]
     * @return
     */
    val country="86"

    val mHandler=Handler()
    val eh = object : EventHandler() {
        override fun afterEvent(event: Int, result: Int, data: Any?) {
            // TODO 此处不可直接处理UI线程，处理后续操作需传到主线程中操作
            val msg = Message()
            msg.arg1 = event
            msg.arg2 = result
            msg.obj = data
            Handler(Looper.getMainLooper(), Handler.Callback {msg ->

                Log.d("GGGGGGGGGG",msg.toString())
                Log.d("hhhhh", msg.data.toString())
                if (msg.arg1 == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                    if (msg.arg2 == SMSSDK.RESULT_COMPLETE){
                      mView?.showSendVerificationResult(true)
                    }else{
                        mView?.showToast("验证码发送失败")
                        mView?.showSendVerificationResult(false)

                    }
                }else if (msg.arg1 ==SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE){
                    if (msg.arg2 ==SMSSDK.RESULT_COMPLETE){
                        mView?.showToast("验证码验证成功")
                        mView?.showCodeVerificationResult(false)
                    }else{
                        mView?.showToast("验证码验证失败是黑白失败")
                        mView?.showCodeVerificationResult(true)
                    }
                }

                false
            }).sendMessage(msg)
        }
    }
    override fun mobilePhoneNumberVerification(phoneNumber: String) {
        MobSDK.submitPolicyGrantResult(true, null)
//注册一个事件回调监听，用于处理SMSSDK接口请求的结果
        SMSSDK.registerEventHandler(eh)
        // 请求验证码，其中country表示国家代码，如“86”；phone表示手机号码，如“13800138000”
        try{
            SMSSDK.getVerificationCode(country, phoneNumber)
        }catch (e:Exception){
            Log.d("HHHHHHHHHHHH",e.message)
        }


//        mView?.showToast("验证码发送成功")

    }

    override fun registerUser(userName: String, phoneNumber: String, password: String) {
        val subscriber = mLoginHelper.register(userName, phoneNumber, password)
            .compose(rxSchedulerHelper())
            .subscribeWith(object : BaseSubscriber<UserApiBean>(mView) {
                override fun onSuccess(mData: UserApiBean) {
                    mView?.showSetTag(mData)
                }
            })
        addSubscribe(subscriber)



    }

    override fun detachView() {
        super.detachView()
        SMSSDK.unregisterEventHandler(eh)
    }

    override fun verifyCode(phoneNumber: String,code: String){
        SMSSDK.submitVerificationCode(country,phoneNumber,code)
    }

    override fun phoneNumberIsOnly(phoneNumber: String,code: String) {
        val subscriber = mLoginHelper.phoneNumberIsOnly(phoneNumber)
            .compose(rxSchedulerHelper())
            .subscribeWith(object : BaseSubscriber<UserApiBean>(mView) {
                override fun onSuccess(mData: UserApiBean) {
                    if (mData.status == 200){
                        mView?.showPhoneNumberIsOnly(false)
//                        verifyCode(phoneNumber, code)
                    }else{
                        mView?.showPhoneNumberIsOnly(true)
                    }
                }
            })
        addSubscribe(subscriber)
    }

    override fun userNameIsOnly(userName: String) {
        val subscriber = mLoginHelper.userNameIsOnly(userName)
            .compose(rxSchedulerHelper())
            .subscribeWith(object : BaseSubscriber<UserApiBean>(mView) {
                override fun onSuccess(mData: UserApiBean) {
                   if (mData.status == 200){
                       mView?.showUserNameIsOnly(false)
                   }else{
                       mView?.showUserNameIsOnly(true)
                   }
                }
            })
        addSubscribe(subscriber)
    }



}