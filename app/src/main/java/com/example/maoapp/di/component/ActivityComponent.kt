package com.example.maoapp.di.component

import android.app.Activity
import com.example.maoapp.MainActivity
import com.example.maoapp.di.module.ActivityModule
import com.example.maoapp.di.scope.ActivityScope
import com.example.maoapp.ui.activity.LoginActivity
import com.example.maoapp.ui.activity.RegisterActivity
import com.example.maoapp.ui.activity.ResetPasswordActivity
import dagger.Component

@ActivityScope
@Component(dependencies = [ApiComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {
    val activity: Activity
    fun inject(activity: MainActivity)

    fun inject(activity: LoginActivity)
    fun inject(activity: RegisterActivity)
    fun inject(activity: ResetPasswordActivity)
}