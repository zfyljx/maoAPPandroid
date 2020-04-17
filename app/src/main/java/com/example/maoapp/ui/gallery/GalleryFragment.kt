package com.example.maoapp.ui.gallery

import android.content.Context
import com.example.maoapp.R
import com.example.maoapp.adapter.HomeAdapter
import com.example.maoapp.base.BaseRefreshFragment
import com.example.maoapp.contract.MineShareContract
import com.example.maoapp.model.bean.ShareModelList
import com.example.maoapp.presenter.MineSharesPresenter
import com.example.maoapp.utils.ToastUtils

class GalleryFragment :  BaseRefreshFragment<MineSharesPresenter, ShareModelList.ShareModel>(), MineShareContract.View  {
    /**
     * 布局
     * @return int
     */

    private var mSharesList = ArrayList<ShareModelList.ShareModel>()
    private var mAdapter : HomeAdapter? =null
    override fun getLayoutId(): Int= R.layout.fragment_gallery
    override fun initPresenter() = mPresenter.attachView(this)

    override fun initInject() = fragmentComponent.inject(this)

//    override fun lazyLoadData() = mPresenter.getShares()
    override fun setShares(shares: ShareModelList) {
    mSharesList.addAll(shares.shares)
    finishTask()
    }

    override fun showToast(tag: String) {
        ToastUtils.showToast(tag)
    }

    override fun initDatas() {
        val userProfile= activity?.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
        val edit=userProfile?.edit()
        val userId=userProfile?.getLong("userId",0) as Long

        mPresenter.getShares(userId)
    }
//    private lateinit var galleryViewModel: GalleryViewModel
//
//    override fun onCreateView(
//            inflater: LayoutInflater,
//            container: ViewGroup?,
//            savedInstanceState: Bundle?
//    ): View? {
//        galleryViewModel =
//                ViewModelProviders.of(this).get(GalleryViewModel::class.java)
//        val root = inflater.inflate(R.layout.fragment_gallery, container, false)
//        val textView: TextView = root.findViewById(R.id.text_gallery)
//        galleryViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
//        return root
//    }

    override fun finishTask() {
        mAdapter?.notifyDataSetChanged()
    }
}
