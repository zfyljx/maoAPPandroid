package com.example.maoapp.ui.activity

import android.os.Bundle
import com.example.maoapp.R
import com.example.maoapp.base.BaseInjectActivity
import com.example.maoapp.contract.LoginContract
import com.example.maoapp.model.apiBean.UserApiBean
import com.example.maoapp.presenter.LoginPresenter
import com.example.maoapp.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseInjectActivity<LoginPresenter>(), LoginContract.View{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())

        loginbtn.setOnClickListener {
            mPresenter.getLogin("13262576795","huangxiao77")
        }


    }

    override fun getLayoutId():Int = R.layout.activity_login

    override fun initInject()=activityComponent.inject(this)
    override fun initPresenter()=mPresenter.attachView(this)

    override fun loadData() {
        super.loadData()
    }

    override fun showToast(tag: UserApiBean) {
       ToastUtils.showToast(tag.message)
    }
//    override fun getLoginResult(result: Boolean) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
//
//    override fun getLoginPhoneNumIsVerified(result: Boolean) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//    }
}
