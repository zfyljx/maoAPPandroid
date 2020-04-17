package com.example.maoapp.base

import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.maoapp.R
import com.example.maoapp.utils.AppUtils
import com.example.maoapp.utils.ToastUtils

abstract class BaseRefreshFragment<T : BaseContract.BasePresenter<*>, K> : BaseInjectFragment<T>(), SwipeRefreshLayout.OnRefreshListener {
    protected var mRecycler: RecyclerView? = null
    protected var mRefresh: SwipeRefreshLayout? = null
    protected var mIsRefreshing = false
    protected var mList = mutableListOf<K>()

    override fun initRefreshLayout() {
        mRefresh?.let {
            it.setColorSchemeResources(R.color.colorPrimary)
            mRecycler?.post {
                it.isRefreshing = true
                lazyLoadData()
            }
            it.setOnRefreshListener(this)
        }
    }

    override fun onRefresh() {
        clearData()
        lazyLoadData()
    }

    override fun clearData() {
        mIsRefreshing = true
    }

    override fun finishCreateView(state: Bundle?) {
        mRefresh = mView?.findViewById(R.id.refresh) as SwipeRefreshLayout?
        mRecycler = mView?.findViewById(R.id.recycler) as RecyclerView?
        mIsPrepared = true
        lazyLoad()
    }

    override fun lazyLoad() {
        if (!mIsPrepared || !mIsVisible) return
        initRefreshLayout()
        initRecyclerView()
        mRefresh ?: lazyLoadData()
        mIsPrepared = false
    }

    override fun complete() {
        AppUtils.runOnUIDelayed({
            mRefresh?.let { it.isRefreshing = false }
        }, 650)
        if (mIsRefreshing) {
            mList.clear()
            clear()
            ToastUtils.showToast("刷新成功")
        }
        mIsRefreshing = false
    }

    protected open fun clear() {
    }

    override fun initWidget() {
    }
}