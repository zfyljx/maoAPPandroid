package com.example.maoapp.ui.activity

import android.content.Context
import android.content.Intent
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.maoapp.R
import com.example.maoapp.base.BaseInjectActivity
import com.example.maoapp.contract.CommodityContract
import com.example.maoapp.model.bean.BannerItem
import com.example.maoapp.model.bean.SellModel
import com.example.maoapp.network.support.ApiConstants
import com.example.maoapp.presenter.CommodityPresenter
import com.example.maoapp.utils.ToastUtils
import com.geek.banner.loader.BannerEntry
import com.geek.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.activity_commodity.*

class CommodityActivity :  BaseInjectActivity<CommodityPresenter>(), CommodityContract.View {
    override fun getLayoutId(): Int = R.layout.activity_commodity
    override fun initInject()=activityComponent.inject(this)
    override fun initPresenter()=mPresenter.attachView(this)

    private var mSellModel: SellModel? = null
    private val mData=ArrayList<BannerItem>()
    override fun initWidget() {

       val intent=intent
        val id=intent.getLongExtra("storeId",1)
        mPresenter.getSellById(id)
        initBanner()
        initListener()
    }

    private fun initListener(){
        commodity_order_submit.setOnClickListener {
            val intent=Intent(this,OrderActivity::class.java)
            intent.putExtra("storeId",mSellModel?.id)
            startActivity(intent)
        }
    }

    private fun initBanner(){
        banner_sell.setBannerLoader(object :ImageLoader<BannerItem>(){
            /**
             * 加载VIEW
             *
             * @param context
             * @param entry
             * @param position  显示的位置
             * @param imageView
             */
            override fun loadView(
                context: Context?,
                entry: BannerEntry<*>?,
                position: Int,
                imageView: ImageView?
            ) {
                val requestOptions=RequestOptions()
                    .placeholder(R.drawable.banner_default)
                    .error(R.drawable.banner_error)
                    .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                context?.let { Glide.with(it).load(entry?.bannerPath).apply(requestOptions).into(imageView as ImageView) }
            }

        }
        )
        banner_sell.loadImagePaths(mData);
    }
    override fun showTost(tag: String) {
       ToastUtils.showToast(tag)
    }

    override fun setCommodity(sell: SellModel) {
       mSellModel=sell
        initData()



    }

    override fun verityPay(result: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    private fun initData(){
        mData.clear()
        if (!mSellModel?.imageOne.isNullOrBlank()){

            mData.add(BannerItem(ApiConstants.QINIU_URL+mSellModel?.imageOne,""))
        }
        if (!mSellModel?.imageTwo.isNullOrBlank()){

            mData.add(BannerItem(ApiConstants.QINIU_URL+mSellModel?.imageTwo,""))
        }
        if (!mSellModel?.imageThree.isNullOrBlank()){

            mData.add(BannerItem(ApiConstants.QINIU_URL+mSellModel?.imageThree,""))
        }

        commodity_name?.text=mSellModel?.name
        commodity_price?.text=mSellModel?.price.toString()
        commodity_description?.text=mSellModel?.description
        commodity_store_name?.text=mSellModel?.storeName
        commodity_store_phone?.text=mSellModel?.userPhone

//        mData.add(BannerItem("http://q87u04f0o.bkt.clouddn.com/ea22d2ee-a3ad-4132-840a-5b075566ab89",""))
//        mData.add(BannerItem("http://q87u04f0o.bkt.clouddn.com/ea22d2ee-a3ad-4132-840a-5b075566ab89",""))
//        mData.add(BannerItem("http://q87u04f0o.bkt.clouddn.com/ea22d2ee-a3ad-4132-840a-5b075566ab89",""))


    }
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_commodity)
//    }
}
