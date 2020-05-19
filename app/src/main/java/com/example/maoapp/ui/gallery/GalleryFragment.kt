package com.example.maoapp.ui.gallery

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maoapp.R
import com.example.maoapp.adapter.MyShareAdapter
import com.example.maoapp.base.BaseRefreshFragment
import com.example.maoapp.contract.MineShareContract
import com.example.maoapp.model.bean.ShareModelList
import com.example.maoapp.presenter.MineSharesPresenter
import com.example.maoapp.utils.ToastUtils
import kotlinx.android.synthetic.main.common_refresh_recycler.*

class GalleryFragment :  BaseRefreshFragment<MineSharesPresenter, ShareModelList.ShareModel>(), MineShareContract.View  {
    /**
     * 布局
     * @return int
     */

    private var mSharesList = ArrayList<ShareModelList.ShareModel>()
    private var mAdapter : MyShareAdapter? =null
    private var mPosition=0
    override fun getLayoutId(): Int= R.layout.fragment_gallery
    override fun initPresenter() = mPresenter.attachView(this)

    override fun initInject() = fragmentComponent.inject(this)

//    override fun lazyLoadData() = mPresenter.getShares()
    override fun setShares(shares: List<ShareModelList.ShareModel>) {
    mSharesList.addAll(shares)
    finishTask()
    }

    override fun showToast(tag: String) {
        ToastUtils.showToast(tag)
    }

    override fun verityStatus(result: Boolean) {
        if (result){
            mSharesList[mPosition].status = 1
            finishTask()
        }else{
            ToastUtils.showToast("网络出错，请重试")
        }
    }

    override fun initSetListener() {
        refresh.setOnRefreshListener {
            lazyLoadData()
            refresh.isRefreshing=false
        }

        mAdapter?.setOnItemChildClickListener { adapter, view, position ->
            Log.d("TAAAAAAAAAAA","HHHHHHHHH")
            when(view.id){
                R.id.order_delivery_submit -> {
                    mPosition=position
                    mPresenter.updateStatus(mSharesList[position].id)
                }
            }
        }
    }

    override fun initDatas() {
        mAdapter = MyShareAdapter(mSharesList)
        recycler?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
//        mRecycler?.layoutManager=
        recycler?.adapter = mAdapter

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
