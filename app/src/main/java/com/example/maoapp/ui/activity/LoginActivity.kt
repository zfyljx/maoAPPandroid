package com.example.maoapp.ui.activity

import android.content.Context
import android.content.Intent
import android.view.View
import com.example.maoapp.MainActivity
import com.example.maoapp.R
import com.example.maoapp.base.BaseInjectActivity
import com.example.maoapp.contract.LoginContract
import com.example.maoapp.model.apiBean.UserApiBean
import com.example.maoapp.presenter.LoginPresenter
import com.example.maoapp.utils.ToastUtils
import com.google.android.material.snackbar.Snackbar
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
            val phoneNumber:String=login_username.text.toString().trim()
            val password:String=login_password.text.toString().trim()
            if (phoneNumber.isNullOrEmpty() || password.isNullOrEmpty()){
               ToastUtils.showToast("请填写手机号与密码")
            }else{
                mPresenter.getLogin(phoneNumber, password)
            }

        }
        login_register.setOnClickListener {
            val intent=Intent(this,RegisterActivity::class.java)
            startActivity(intent)
        }

        login_forget_password.setOnClickListener {
            val intent=Intent(this,ResetPasswordActivity::class.java)
            startActivity(intent)
        }

    }
    override fun loadData() {
        super.loadData()
    }


    override fun showToast(tag: UserApiBean) {
        if (tag.status.equals("200")){
            ToastUtils.showToast("登陆成功")
            val userProfile=getSharedPreferences("userProfile",Context.MODE_PRIVATE)
            val edit=userProfile.edit()
            edit.putString("userName",tag.data.userName)
            edit.putString("phoneNumber",tag.data.phoneNumber)
            edit.putString("description",tag.data.description)
            edit.apply()

            val intent=Intent(this@LoginActivity,MainActivity::class.java)
            startActivity(intent)
        }else if (tag.status.equals("203")){
            createSnackBar(login_password,"密码错误")
        }else{
            createSnackBar(login_username,"手机号未注册")
        }

    }

    override fun navigateToMain() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun createSnackBar(view: View, msg:String){
        Snackbar.make(view,msg, Snackbar.LENGTH_LONG)
    }
}
