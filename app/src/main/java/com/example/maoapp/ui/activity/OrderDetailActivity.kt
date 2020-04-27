package com.example.maoapp.ui.activity

import com.example.maoapp.R
import com.example.maoapp.base.BaseInjectActivity
import com.example.maoapp.contract.OrderDetailContract
import com.example.maoapp.model.bean.OrderModel
import com.example.maoapp.network.support.ApiConstants
import com.example.maoapp.presenter.OrderDetailPresenter
import com.example.maoapp.utils.ToastUtils
import com.liulishuo.qiniuimageloader.utils.PicassoLoader
import kotlinx.android.synthetic.main.activity_order_detail.*

class OrderDetailActivity : BaseInjectActivity<OrderDetailPresenter>(), OrderDetailContract.View {
    override fun getLayoutId(): Int = R.layout.activity_order_detail
    override fun initInject()=activityComponent.inject(this)
    override fun initPresenter()=mPresenter.attachView(this)

    private var mOrder:OrderModel?=null
    override fun initWidget() {
        val intent= intent
        val orderId=intent.getLongExtra("orderId",1)
        mPresenter.getOrder(orderId)
    }
    override fun showToast(tag: String) {
        ToastUtils.showToast(tag)
    }

    override fun setOrder(order: OrderModel) {
       mOrder=order
        initData()
    }

    override fun verityStatus(result: Boolean) {

    }

    private fun initData(){
        PicassoLoader.createLoader(detail_image, ApiConstants.QINIU_URL+mOrder?.imageOne)
                                .attach()
        detail_id.text=mOrder?.id.toString()
        detail_create_time.text=mOrder?.createTime
        detail_dead_time.text=mOrder?.deadTime
        detail_delivery_code.text=mOrder?.deliveryCode
        detail_name.text=mOrder?.name
        detail_number.text=mOrder?.number.toString()
        detail_price.text=mOrder?.price.toString()
        detail_total.text=mOrder?.totalPrice.toString()
        detail_store_name.text=mOrder?.storeName
        detail_user_name.text=mOrder?.userName
        detail_user_phone.text=mOrder?.userPhone
        detail_user_address.text=mOrder?.userAddress
        if (mOrder?.status == 1){
            detail_status.text="已收货"
        }else{
            detail_status.text="待收货"
        }



    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_order_detail)
//    }
}
