package com.example.maoapp

import android.app.Activity
import android.app.Application
import com.example.maoapp.di.component.ApiComponent
import com.example.maoapp.di.component.DaggerApiComponent
import com.example.maoapp.di.module.ApiModule
import com.example.maoapp.utils.AppUtils
import com.example.maoapp.utils.CrashHandler
import com.example.maoapp.utils.LogUtils
import com.example.maoapp.utils.NetworkUtils

class MaoApplication :Application(){

    private var allActivities: HashSet<Activity>? = null
    val mApiComponent: ApiComponent by lazy {
        DaggerApiComponent.builder()
            .apiModule(ApiModule())
            .build()
    }
//    private val mSp = getSharedPreferences(Constants.EXTRA_COOKIE, Context.MODE_PRIVATE)

    override fun onCreate() {
        super.onCreate()
        AppUtils.init(this)
        instance = this
        initNetwork()
        initCrashHandler()
        initLog()
    }

    companion object {
        lateinit var instance: MaoApplication
    }

//    fun getCookies(): HashSet<String> =
//            mSp.getStringSet(Constants.EXTRA_COOKIE, HashSet<String>()) as HashSet<String>
//
//
//    fun setCookies(cookies: java.util.HashSet<String>) =
//            mSp.edit().putStringSet(Constants.EXTRA_COOKIE, cookies).apply()

    private fun initNetwork() = NetworkUtils.startNetService(this)

    private fun initCrashHandler() = CrashHandler.init(this)

    private fun initLog() = LogUtils.init(this)


    /**
     * 增加Activity
     * @param act act
     */
    fun addActivity(act: Activity) {
        if (allActivities == null) {
            allActivities = HashSet()
        } else {
            allActivities?.add(act)
        }
    }

    /**
     * 移除Activity
     * @param act act
     */
    fun removeActivity(act: Activity) {
        allActivities?.remove(act)
    }

    @Synchronized
    fun exitApp() {
        allActivities?.let {
            it.forEach { activity ->
                activity.finish()
            }
        }
        android.os.Process.killProcess(android.os.Process.myPid())
        System.exit(0)
    }
}