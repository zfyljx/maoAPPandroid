package com.example.maoapp.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.maoapp.R
import com.example.maoapp.model.bean.SellModel
import com.example.maoapp.network.support.ApiConstants
import com.liulishuo.qiniuimageloader.utils.PicassoLoader

class SellAdapter(data: List<SellModel>) : BaseQuickAdapter<SellModel, BaseViewHolder>(
    R.layout.layout_sell_card, data) {
    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    override fun convert(helper: BaseViewHolder, item: SellModel) {
        with(helper) {
            item.let {
               val imageOne=getView<ImageView>(R.id.sell_image)
                PicassoLoader.createLoader(imageOne, ApiConstants.QINIU_URL + item.imageOne)
                    .attach()
                setText(R.id.sell_intro,item.introduction)
                setText(R.id.sell_name,item.name)
                setText(R.id.sell_number,item.monthSell.toString())
                setText(R.id.sell_price,item.price.toString())

            }}

    }


    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
    }


}