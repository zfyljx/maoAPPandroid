package com.example.maoapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.maoapp.R
import com.example.maoapp.ui.interfaces.IRegisterActivity

class RegisterActivity : AppCompatActivity(),IRegisterActivity {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    override fun showUserNameResult(result: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showSendVerificationResult(result: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showRegisterResult(result: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun checkPhoneNumVerified(result: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
