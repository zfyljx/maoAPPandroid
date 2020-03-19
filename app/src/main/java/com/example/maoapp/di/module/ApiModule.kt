package com.example.maoapp.di.module

import com.example.maoapp.di.qualifier.AppUrl
import com.example.maoapp.network.api.ApiService
import com.example.maoapp.network.api.LoginService
import com.example.maoapp.network.helper.LoginHelper
import com.example.maoapp.network.helper.OkHttpHelper
import com.example.maoapp.network.helper.RetrofitHelper
import com.example.maoapp.network.support.ApiConstants
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApiModule {
    fun createRetrofit(builder: Retrofit.Builder, client: OkHttpClient, baseUrl: String): Retrofit {
        return builder
            .baseUrl(baseUrl)
            .client(client)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpHelper.getOkHttpClient()

    @Provides
    @Singleton
    fun provideRetrofitBuilder(): Retrofit.Builder {
        return Retrofit.Builder()
    }

    @Provides
    @Singleton
    fun provideRetrofitHelper(apiService: ApiService): RetrofitHelper {
        return RetrofitHelper(apiService)
    }

    @Provides
    @Singleton
    fun provideLoginHelper(apiService: LoginService): LoginHelper {
        return LoginHelper(apiService)
    }

    @Singleton
    @Provides
    @AppUrl
    fun provideAppRetrofit(builder: Retrofit.Builder, client: OkHttpClient): Retrofit {
        return createRetrofit(builder, client, ApiConstants.APP_BASE_URL)
    }

    @Singleton
    @Provides
    fun provideApiService(@AppUrl retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }


    @Singleton
    @Provides
    fun provideLoginService(@AppUrl retrofit: Retrofit): LoginService {
        return retrofit.create(LoginService::class.java)
    }
}