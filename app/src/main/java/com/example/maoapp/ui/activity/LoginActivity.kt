package com.example.maoapp.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.maoapp.R
import com.example.maoapp.ui.interfaces.ILoginActivity

class LoginActivity : AppCompatActivity(),ILoginActivity{

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun getLoginResult(result: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getLoginPhoneNumIsVerified(result: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
