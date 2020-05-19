package com.example.maoapp.presenter

import com.example.maoapp.base.BaseSubscriber
import com.example.maoapp.base.RxPresenter
import com.example.maoapp.contract.WriteContract
import com.example.maoapp.model.apiBean.LocationBean
import com.example.maoapp.model.apiBean.QiniuToken
import com.example.maoapp.model.apiBean.ResultNoDataBean
import com.example.maoapp.network.helper.LoginHelper
import com.example.maoapp.utils.rxSchedulerHelper
import javax.inject.Inject

class WritePresenter@Inject constructor(private val mLoginHelper: LoginHelper) :
    RxPresenter<WriteContract.View>(), WriteContract.Presenter<WriteContract.View>  {
    override fun getQiniuToken() {
        val subscriber = mLoginHelper.getQiniuToken()
            .compose(rxSchedulerHelper())
            .subscribeWith(object : BaseSubscriber<QiniuToken>(mView) {
                override fun onSuccess(mData: QiniuToken) {
                   if (mData.status == 200){
                       mView?.setToken(mData.data.token)
                   }
                }
            })
        addSubscribe(subscriber)
    }

    override fun getAdressDetail(longitude: String, latitude: String) {
        //TODO
        //真机模拟  双山"121.4235220000", "31.2601760000"
        val subscriber = mLoginHelper.getAdressDetail("121.5541774000", "31.29381673000")
            .compose(rxSchedulerHelper())
            .subscribeWith(object : BaseSubscriber<LocationBean>(mView) {
                override fun onSuccess(mData: LocationBean) {
                    if (mData.status == 200){
                        mView?.setAddressDetail(mData.data.address)
                    }else{
                        mView?.showToast("网络定位有问题，请重试")
                    }
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    mView?.showToast("网络定位有问题，请重试")
                }
            })
        addSubscribe(subscriber)
    }

    override fun uploadShare(
        id: Long,
        userName:String,
        message: String,
        address: String,
        imageOne: String,
        imageTwo: String,
        imageThree: String,
        classification:Int,
        userImage:String
    ) {
        val subscriber = mLoginHelper.upLoadShare(id,userName, message, address, imageOne, imageTwo, imageThree,classification, userImage)
            .compose(rxSchedulerHelper())
            .subscribeWith(object : BaseSubscriber<ResultNoDataBean>(mView) {
                override fun onSuccess(mData: ResultNoDataBean) {
                    if (mData.status == 200){
                       mView?.showUploadShareResult(true)
                    }else{
                        mView?.showUploadShareResult(false)
                    }
                }

                override fun onError(e: Throwable) {
                    super.onError(e)
                    mView?.showToast("发布失败，请检查网路稍后再试")
                }
            })
        addSubscribe(subscriber)
    }
}