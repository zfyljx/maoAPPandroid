package com.example.maoapp.ui.activity

import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.example.maoapp.MainActivity
import com.example.maoapp.R
import com.example.maoapp.base.BaseInjectActivity
import com.example.maoapp.contract.OrderConfirmContract
import com.example.maoapp.model.bean.SellModel
import com.example.maoapp.network.support.ApiConstants
import com.example.maoapp.presenter.OrderConfirmPresenter
import com.example.maoapp.utils.ToastUtils
import com.liulishuo.qiniuimageloader.utils.PicassoLoader
import kotlinx.android.synthetic.main.activity_order.*

class OrderActivity : BaseInjectActivity<OrderConfirmPresenter>(), OrderConfirmContract.View {

//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_order)
//    }

    override fun getLayoutId(): Int =R.layout.activity_order
    override fun initInject()=activityComponent.inject(this)
    override fun initPresenter()=mPresenter.attachView(this)

    private var mSellModel: SellModel? = null
    override fun initWidget() {
        val intent= intent
        val id=intent.getLongExtra("storeId",1)
        mPresenter.getSellById(id)
        initListener()
    }

    private fun initListener(){

        order_add.setOnClickListener{
            var number=order_number.text.toString().toInt()
            number += 1
            var price=order_price.text.toString().toFloat()
            var totalPrice=order_total.text.toString().toFloat()
            totalPrice=price * number
            order_number.text=number.toString()
            order_total.text=totalPrice.toString()

        }

        order_minus.setOnClickListener {
            var number=order_number.text.toString().toInt()
            if (number == 1){
                order_minus.visibility=View.INVISIBLE
            }else{
                order_minus.visibility=View.VISIBLE
                number -= 1
                var price=order_price.text.toString().toFloat()
                var totalPrice=order_total.text.toString().toFloat()
                totalPrice=price * number
                order_number.text=number.toString()
                order_total.text=totalPrice.toString()
            }

            if (number == 1){
                order_minus.visibility=View.INVISIBLE
            }

        }

        order_submit.setOnClickListener {
            if (!checkALLDataBlank()){
                val dialog= AlertDialog.Builder(this)
                    .setTitle("确认支付")
                    .setIcon(R.drawable.is_back)
                    .setMessage("确定要支付 "+order_total.text.toString()+" 元吗")
                    .setPositiveButton("支付", DialogInterface.OnClickListener { _, _ ->
                        val userProfile= getSharedPreferences("userProfile", Context.MODE_PRIVATE)
                        val edit=userProfile?.edit()
                        val userId=userProfile?.getLong("userId",0) as Long
                        mPresenter.buildOrder(mSellModel?.id as Long,mSellModel?.storeId as Long,mSellModel?.storeName as String,order_name.text.toString(),userId,order_name.text.toString(),order_user_phone.text.toString(),order_user_address.text.toString(),mSellModel?.imageOne as String,mSellModel?.price as Float,order_number.text.toString().toInt(),order_total.text.toString().toFloat())
                    })
                    .setNegativeButton("取消", DialogInterface.OnClickListener { _, _ ->  })
                    .create().show()

            }else{
                ToastUtils.showToast("请填写完成收件人信息")
            }
        }

    }
    override fun showTost(tag: String) {
        ToastUtils.showToast(tag)
    }

    override fun setCommodity(sell: SellModel) {
        mSellModel=sell
        PicassoLoader.createLoader(order_image, ApiConstants.QINIU_URL + sell.imageOne)
            .attach()

        order_name.text=sell.name
        order_price.text=sell.price.toString()
        order_total?.text=sell.price.toString()

    }

    override fun verityPay(result: Boolean) {
        if (result){
            ToastUtils.showToast("下单成功，请在我的订单中查询")
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
        }else{
            ToastUtils.showToast("下单失败，请检查网络稍后再试")
        }
    }

    private fun checkALLDataBlank():Boolean{
        val dadalist= arrayListOf<String>()
        dadalist.add(order_user_name.text.toString())
        dadalist.add(order_user_phone.text.toString())
        dadalist.add(order_user_address.text.toString())
        return dadalist.contains("")
    }
}
