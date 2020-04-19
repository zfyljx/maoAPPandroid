package com.example.maoapp.ui.activity

import com.example.maoapp.R
import com.example.maoapp.base.BaseInjectActivity
import com.example.maoapp.contract.CommodityContract
import com.example.maoapp.model.bean.SellModel
import com.example.maoapp.presenter.CommodityPresenter
import com.example.maoapp.utils.ToastUtils

class CommodityActivity :  BaseInjectActivity<CommodityPresenter>(), CommodityContract.View {
    override fun getLayoutId(): Int = R.layout.activity_commodity
    override fun initInject()=activityComponent.inject(this)
    override fun initPresenter()=mPresenter.attachView(this)

    override fun showTost(tag: String) {
       ToastUtils.showToast(tag)
    }

    override fun setCommodity(sell: SellModel) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun verityPay(result: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_commodity)
//    }
}
