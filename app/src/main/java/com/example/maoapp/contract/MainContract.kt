package com.example.maoapp.contract

import com.example.maoapp.base.BaseContract


/**
 * @author: ym  作者 E-mail: 15622113269@163.com
 * date: 2018/11/16
 * desc: 主界面- MainContract
 *
 */
interface MainContract {

    interface View : BaseContract.BaseView {

        //activity的方法
//        fun showDiscoveryBean(mDiscoveryCommentBean: DiscoveryCommentBean)

//        fun showSetTag(tagSuccessBean: TagSuccessBean)
    }

    interface Presenter<in T> : BaseContract.BasePresenter<T> {

        /**
         * @param tagId [所处阶段Id,阶段对应级别（如初中->初一、初二、初三）]
         * @return
         */
//        fun getRegionTagTypeBean(tagId: ArrayList<Int>)

        //api方法
    }
}