package com.example.maoapp.ui.activity

import com.example.maoapp.R
import com.example.maoapp.base.BaseInjectActivity
import com.example.maoapp.contract.WriteContract
import com.example.maoapp.presenter.WritePresenter

class WriteActivity : BaseInjectActivity<WritePresenter>(), WriteContract.View{


    override fun getLayoutId(): Int=R.layout.activity_write



    override fun initInject()=activityComponent.inject(this)
    override fun initPresenter()=mPresenter.attachView(this)


    override fun initWidget() {

    }

    override fun loadData() {

    }

    override fun initVariables() {

    }
}
