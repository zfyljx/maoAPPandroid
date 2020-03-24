package com.example.maoapp.ui.activity

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import com.example.maoapp.R
import com.example.maoapp.base.BaseInjectActivity
import com.example.maoapp.contract.RegisterContract
import com.example.maoapp.model.apiBean.UserApiBean
import com.example.maoapp.presenter.RegisterPresenter
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : BaseInjectActivity<RegisterPresenter>(), RegisterContract.View {



    override fun getLayoutId(): Int=R.layout.activity_register
    override fun initInject()=activityComponent.inject(this)
    override fun initPresenter()=mPresenter.attachView(this)
    override fun initWidget() {

        register_phone_number.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (register_phone_number.length() == 11){
                    register_get_verification_code.visibility= View.VISIBLE
                }else{
                    register_get_verification_code.visibility= View.INVISIBLE
                }
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })





    }

    override fun showSetTag(tagSuccessBean: UserApiBean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun navigateToMain() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
