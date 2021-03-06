package com.example.maoapp.adapter

import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
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
                val textStatus=getView<TextView>(R.id.share_status)
                if (item.classification == 0){
                    if (item.status == 0){
                        setText(R.id.share_status,"未认领")
                    }else{
                        setText(R.id.share_status,"已认领")
                    }
                }else{
                    textStatus.visibility=View.GONE
                }

                if (!item.userImage.isNullOrBlank()){
                    val userImage: ImageView = getView(R.id.share_user_image)
                    PicassoLoader.createLoader(userImage, ApiConstants.QINIU_URL + item.userImage)
                        .attach()
                }

                if (!item.imageOne.isNullOrBlank()) {
                    val imageOne: ImageView = getView(R.id.share_pic_one)
                    imageOne.visibility=View.VISIBLE
                    PicassoLoader.createLoader(imageOne, ApiConstants.QINIU_URL + item.imageOne)
                        .attach()

                    if (!item.imageTwo.isNullOrBlank()) {
                        val imageTwo: ImageView = getView(R.id.share_pic_second)
                        imageTwo.visibility=View.VISIBLE
                        PicassoLoader.createLoader(imageTwo, ApiConstants.QINIU_URL + item.imageTwo)
                            .attach()
                    }

                    if (!item.imageThree.isNullOrBlank()) {
                        val imageThree: ImageView = getView(R.id.share_pic_three)
                        imageThree.visibility=View.VISIBLE
                        PicassoLoader.createLoader(imageThree, ApiConstants.QINIU_URL + item.imageTwo)
                            .attach()
                    }
                }else
                {
                    getView<LinearLayout>(R.id.share_linear).visibility=View.GONE
                }
                helper.addOnClickListener(R.id.share_user_image)

            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}