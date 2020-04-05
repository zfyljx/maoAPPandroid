package com.example.maoapp.ui.activity

import android.content.Context
import android.content.Intent
import android.os.CountDownTimer
import android.os.Handler
import android.os.Message
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.maoapp.MainActivity
import com.example.maoapp.R
import com.example.maoapp.base.BaseInjectActivity
import com.example.maoapp.contract.RegisterContract
import com.example.maoapp.model.apiBean.UserApiBean
import com.example.maoapp.presenter.RegisterPresenter
import com.example.maoapp.utils.ToastUtils
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseInjectActivity<RegisterPresenter>(), RegisterContract.View {



    lateinit var mHandler:Handler
    var checkUsername=true
    var checkPhoneNumber=true
    var checkCode=false
    override fun getLayoutId(): Int=R.layout.activity_register
    override fun initInject()=activityComponent.inject(this)
    override fun initPresenter()=mPresenter.attachView(this)
    override fun initWidget() {

        register_phone_number.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (register_phone_number.length() == 11){
                    register_get_verification_code.visibility= View.VISIBLE
                }else{
                    register_get_verification_code.visibility= View.INVISIBLE
                }
            }
        })


        register_get_verification_code.setOnClickListener {
            mPresenter.mobilePhoneNumberVerification(register_phone_number.text.toString().trim())
            var countDownTimer=object : CountDownTimer(60*1000,1000){
                /**
                 * Callback fired when the time is up.
                 */
                override fun onFinish() {
                    register_get_verification_code.isEnabled=true
                    register_get_verification_code.text="重新发送"
                }

                /**
                 * Callback fired on regular interval.
                 * @param millisUntilFinished The amount of time until finished.
                 */
                override fun onTick(millisUntilFinished: Long) {
                    var seconds=(millisUntilFinished/1000).toInt().toString()
                    register_get_verification_code.isEnabled=false
                    register_get_verification_code.text=seconds+"s"
                }

            }.start()
        }

        register_register.setOnClickListener {
            mPresenter.userNameIsOnly(register_nickname.text.toString().trim())


            mHandler=Handler(){
             when(it.what){
                  1 -> mPresenter.phoneNumberIsOnly(register_phone_number.text.toString().trim(),register_verification_code.text.toString().trim())
                  2-> mPresenter.verifyCode(register_phone_number.text.toString().trim(),register_verification_code.text.toString().trim())
                  3 -> {
                      if (checkALLDataBlank()){
                          ToastUtils.showToast("请填写完成以上内容")
                          createSnackBar(register_password_again,"请填写完成以上内容")
                      }else if (checkUsername){
                          ToastUtils.showToast("该用户名已被注册")
                          createSnackBar(register_nickname,"该用户名已被注册")
                      }else if (checkPhoneNumber){
                          ToastUtils.showToast("该手机号已被注册")
                          createSnackBar(register_phone_number,"该手机号已被注册")
                      }else if (checkCode){
                          ToastUtils.showToast("验证码不正确")
                          createSnackBar(register_verification_code,"验证码不正确")
                      }else if (! register_password_again.text.toString().equals(register_password.text.toString())){
                          ToastUtils.showToast("两次密码不一样，请重新输入")
                          createSnackBar(register_password_again,"两次密码不一样，请重新输入")
                      }else{
                          mPresenter.registerUser(register_nickname.text.toString().trim(),register_phone_number.text.toString().trim(),register_password.text.toString().trim())
                      }
                  }
                 else -> print("message")
             }
                false
            }
        }




    }

    override fun showSetTag(tag: UserApiBean) {
        if (tag.status==200){
            ToastUtils.showToast("注册成功")
            val userProfile=getSharedPreferences("userProfile", Context.MODE_PRIVATE)
            val edit=userProfile.edit()
            edit.putLong("userId",tag.data.id)
            edit.putString("userName",tag.data.userName)
            edit.putString("phoneNumber",tag.data.phoneNumber)
            edit.putString("description",tag.data.description)
            edit.apply()

            val intent= Intent(this@RegisterActivity, MainActivity::class.java)
            startActivity(intent)
        }else{
            ToastUtils.showToast("注册失败")
        }

    }

    override fun showToast(meg: String) {
        ToastUtils.showToast(meg)
    }

    override fun navigateToMain() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSendVerificationResult(result: Boolean) {
        if (result){
            ToastUtils.showToast("验证码发送成功")
            createSnackBar(register_get_verification_code,"验证码发送成功")
        }else{
            ToastUtils.showToast("验证码发送失败，请检查手机号是否正确")
            createSnackBar(register_get_verification_code,"验证码发送失败，请检查手机号是否正确")
        }
    }

    override fun showCodeVerificationResult(result: Boolean) {
        checkCode = result
        val message=Message()
        message.what=3
        mHandler.sendMessage(message)
//        if (result){
//            ToastUtils.showToast("验证成功")
//            createSnackBar(register_get_verification_code,"验证成功")
//        }else{
//            ToastUtils.showToast("验证失败")
//            createSnackBar(register_get_verification_code,"验证失败")
//        }
    }

    override fun showUserNameIsOnly(result: Boolean) {
        checkUsername = result
        val message=Message()
        message.what=1
        mHandler.sendMessage(message)
    }

    override fun showPhoneNumberIsOnly(result: Boolean) {
        checkPhoneNumber = result
        val message=Message()
        message.what=2
        mHandler.sendMessage(message)
    }

    override fun showCodeResult(result: Boolean) {
        checkCode=result
    }

    private fun createSnackBar(view: View,msg:String){
           Snackbar.make(view,msg,Snackbar.LENGTH_LONG)
    }

    private fun checkALLDataBlank():Boolean{
       val dadalist= arrayListOf<String>()
        dadalist.add(register_nickname.text.toString())
        dadalist.add(register_phone_number.text.toString())
        dadalist.add(register_verification_code.text.toString())
        dadalist.add(register_password.text.toString())
        dadalist.add(register_password_again.text.toString())
        return dadalist.contains("")
    }

}
