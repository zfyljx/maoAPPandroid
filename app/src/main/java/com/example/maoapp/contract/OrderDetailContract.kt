package com.example.maoapp.contract

import com.example.maoapp.base.BaseContract
import com.example.maoapp.model.bean.OrderModel

interface OrderDetailContract {
    interface View : BaseContract.BaseView {

//        fun showDiscoveryBean(mDiscoveryCommentBean: DiscoveryCommentBean)

        //        fun showSetTag(tagSuccessBean: TagSuccessBean)
        fun showToast(tag: String)
        fun setOrder(order:OrderModel)
        fun verityStatus(result:Boolean)
    }

    interface Presenter<in T> : BaseContract.BasePresenter<T> {

        /**
         * @param tagId [所处阶段Id,阶段对应级别（如初中->初一、初二、初三）]
         * @return
         */
//        fun getRegionTagTypeBean(tagId: ArrayList<Int>)

        fun updateStatus(orderId: Long)
        fun getOrder(orderId:Long)
    }
}