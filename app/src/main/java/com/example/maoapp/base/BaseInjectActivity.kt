package com.example.maoapp.base

import com.example.maoapp.MaoApplication
import com.example.maoapp.di.component.ActivityComponent
import com.example.maoapp.di.component.DaggerActivityComponent
import com.example.maoapp.di.module.ActivityModule

import javax.inject.Inject

/**
 * @author: ym  作者 E-mail: 15622113269@163.com
 * date: 2018/11/8
 * desc: 基础注入Activity
 *
 */
abstract class BaseInjectActivity<T : BaseContract.BasePresenter<*>> : BaseActivity(), BaseContract.BaseView {

    @Inject
    lateinit var mPresenter: T

    protected val activityModule: ActivityModule get() = ActivityModule(this)

    protected val activityComponent: ActivityComponent
        get() = DaggerActivityComponent.builder()
                .apiComponent(MaoApplication.instance.mApiComponent)
                .activityModule(activityModule)
                .build()

    override fun onDestroy() {
        mPresenter.detachView()
        MaoApplication.instance.removeActivity(this)
        super.onDestroy()
    }

    override fun showError(msg: String) {
    }

    override fun complete() {
    }
}