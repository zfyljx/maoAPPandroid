package com.example.maoapp.adapter

import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.maoapp.R
import com.example.maoapp.model.bean.ShareModelList
import com.example.maoapp.network.support.ApiConstants
import com.liulishuo.qiniuimageloader.utils.PicassoLoader

class MyShareAdapter(data: List<ShareModelList.ShareModel>) : BaseQuickAdapter<ShareModelList.ShareModel, BaseViewHolder>(
    R.layout.layout_my_shares, data) {
    /**
     * Implement this method and use the helper to adapt the view to the given item.
     *
     * @param helper A fully initialized helper.
     * @param item   The item that needs to be displayed.
     */
    override fun convert(helper: BaseViewHolder, item: ShareModelList.ShareModel) {
        with(helper) {
            item.let {
                setText(R.id.my_share_user_name, item.userName)
                setText(R.id.my_share_create_time, item.createTime)
                setText(R.id.my_share_message, item.message)
                setText(R.id.my_share_address, item.address)
                val textStatus=getView<TextView>(R.id.my_share_status)
                val buttonStatus=getView<Button>(R.id.my_share_status_button)
                if (item.classification == 0){
                    if (item.status == 0){
                       textStatus.visibility=View.GONE
                    }else{
                       buttonStatus.visibility=View.GONE
                    }
                }else{
                    textStatus.visibility= View.GONE
                    buttonStatus.visibility=View.GONE
                }

                if (!item.userImage.isNullOrBlank()){
                    val userImage: ImageView = getView(R.id.my_share_user_image)
                    PicassoLoader.createLoader(userImage, ApiConstants.QINIU_URL + item.userImage)
                        .attach()
                }

                if (!item.imageOne.isNullOrBlank()) {
                    val imageOne: ImageView = getView(R.id.my_share_pic_one)
                    imageOne.visibility= View.VISIBLE
                    PicassoLoader.createLoader(imageOne, ApiConstants.QINIU_URL + item.imageOne)
                        .attach()

                    if (!item.imageTwo.isNullOrBlank()) {
                        val imageTwo: ImageView = getView(R.id.my_share_pic_second)
                        imageTwo.visibility= View.VISIBLE
                        PicassoLoader.createLoader(imageTwo, ApiConstants.QINIU_URL + item.imageTwo)
                            .attach()
                    }

                    if (!item.imageThree.isNullOrBlank()) {
                        val imageThree: ImageView = getView(R.id.my_share_pic_three)
                        imageThree.visibility= View.VISIBLE
                        PicassoLoader.createLoader(imageThree, ApiConstants.QINIU_URL + item.imageTwo)
                            .attach()
                    }
                }else
                {
                    getView<LinearLayout>(R.id.my_share_linear).visibility= View.GONE
                }
                helper.addOnClickListener(R.id.my_share_status_button)

            }
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }
}