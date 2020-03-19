package com.example.maoapp.di.component

import com.example.maoapp.di.module.ApiModule
import com.example.maoapp.network.helper.LoginHelper
import com.example.maoapp.network.helper.RetrofitHelper
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApiModule::class])
interface ApiComponent {
    val retrofitHelper: RetrofitHelper
    val loginHelper:LoginHelper
}