package com.example.maoapp.ui.activity

import com.example.maoapp.R
import com.example.maoapp.base.BaseInjectActivity
import com.example.maoapp.contract.LoginContract
import com.example.maoapp.model.apiBean.UserApiBean
import com.example.maoapp.presenter.LoginPresenter
import com.example.maoapp.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseInjectActivity<LoginPresenter>(), LoginContract.View{

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(getLayoutId())
//
//
//
//
//    }

    override fun getLayoutId():Int = R.layout.activity_login

    override fun initInject()=activityComponent.inject(this)
    override fun initPresenter()=mPresenter.attachView(this)

    override fun initWidget() {
        loginbtn.setOnClickListener {
            val phoneNumber:String=login_username.toString().trim()
            val password:String=login_password.toString().trim()
            if (phoneNumber.isNullOrEmpty() || password.isNullOrEmpty()){
                showMessage("请填写手机号与密码")
            }else{
                mPresenter.getLogin(phoneNumber, password)
            }

        }
    }
    override fun loadData() {
        super.loadData()
    }

    fun showMessage(msg:String){
        ToastUtils.showToast(msg)
    }
    override fun showToast(tag: UserApiBean) {
       ToastUtils.showToast(tag.message)
    }

    override fun navigateToMain() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
