package com.example.maoapp.adapter

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.maoapp.R
import com.example.maoapp.model.bean.OrderModel
import com.example.maoapp.network.support.ApiConstants
import com.liulishuo.qiniuimageloader.utils.PicassoLoader

class OrderAdapter(data: List<OrderModel>) : BaseQuickAdapter<OrderModel, BaseViewHolder>(
    R.layout.layout_order_card, data) {
    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    override fun convert(helper: BaseViewHolder, item: OrderModel) {
        with(helper) {
            item.let {
                val imageOne= getView<ImageView>(R.id.order_image_one)
                PicassoLoader.createLoader(imageOne, ApiConstants.QINIU_URL + item.imageOne)
                    .attach()
                setText(R.id.order_id,item.id.toString())
                setText(R.id.order_good_name,item.name)
                setText(R.id.order_create_time,item.createTime)
                setText(R.id.order_good_number,item.number.toString())
                setText(R.id.order_good_price,item.price.toString())
                setText(R.id.order_delivery_code,item.deliveryCode)
                setText(R.id.order_total,item.totalPrice.toString())
                val button = getView<Button>(R.id.order_delivery_submit)
                val textView=getView<TextView>(R.id.order_status)

                if (item.status == 1){

                    button.visibility=View.GONE
                    textView.visibility=View.VISIBLE
                }

                helper.addOnClickListener(R.id.order_delivery_submit)
                }


            }}
    }
