package com.example.maoapp.adapter

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.maoapp.R
import com.example.maoapp.model.bean.ShareModelList
import com.example.maoapp.network.support.ApiConstants
import com.liulishuo.qiniuimageloader.utils.PicassoLoader

class HomeAdapter(data: List<ShareModelList.ShareModel>) : BaseQuickAdapter<ShareModelList.ShareModel, BaseViewHolder>(
    R.layout.layout_home_shares, data) {
    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    override fun convert(helper: BaseViewHolder, item: ShareModelList.ShareModel) {
        with(helper) {
            item.let {
                setText(R.id.share_user_name, item.userName)
                setText(R.id.share_create_time, item.createTime)
                setText(R.id.share_message, item.message)
                setText(R.id.share_address, item.address)

                if (!item.imageOne.isNullOrBlank()) {
                    getView<LinearLayout>(R.id.share_linear).visibility= View.INVISIBLE
                    val imageOne: ImageView = getView(R.id.share_pic_one)
                    imageOne.visibility=View.INVISIBLE
                    PicassoLoader.createLoader(imageOne, ApiConstants.QINIU_URL + item.imageOne)
                        .attach()
                }

                if (!item.imageTwo.isNullOrBlank()) {
                    val imageTwo: ImageView = getView(R.id.share_pic_second)
                    imageTwo.visibility=View.INVISIBLE
                    PicassoLoader.createLoader(imageTwo, ApiConstants.QINIU_URL + item.imageTwo)
                        .attach()
                }

                if (!item.imageThree.isNullOrBlank()) {
                    val imageThree: ImageView = getView(R.id.share_pic_three)
                    imageThree.visibility=View.INVISIBLE
                    PicassoLoader.createLoader(imageThree, ApiConstants.QINIU_URL + item.imageTwo)
                        .attach()
                }
            }
        }
    }
}