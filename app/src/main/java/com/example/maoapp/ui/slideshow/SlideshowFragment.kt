package com.example.maoapp.ui.slideshow

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maoapp.R
import com.example.maoapp.adapter.SellAdapter
import com.example.maoapp.base.BaseRefreshFragment
import com.example.maoapp.contract.SellFragmentContract
import com.example.maoapp.model.bean.SellModel
import com.example.maoapp.presenter.SellFragmentPresenter
import com.example.maoapp.ui.activity.CommodityActivity
import com.example.maoapp.utils.ToastUtils
import kotlinx.android.synthetic.main.common_refresh_recycler.*

class SlideshowFragment : BaseRefreshFragment<SellFragmentPresenter, SellModel>(), SellFragmentContract.View {

    private var mSellList=ArrayList<SellModel>()
    private var mAdapter:SellAdapter?=null

    /**
     * 布局
     * @return int
     */
    override fun getLayoutId(): Int = R.layout.fragment_slideshow
    override fun initPresenter() = mPresenter.attachView(this)

    override fun initInject() = fragmentComponent.inject(this)

    override fun lazyLoadData() = mPresenter.getSells()

    override fun setSells(sells: List<SellModel>) {
        mSellList.clear()
        mSellList.addAll(sells)
        finishTask()
    }

    override fun showToast(tag: String) {
        ToastUtils.showToast(tag)
    }

    companion object {
        fun newInstance(id: Int): SlideshowFragment {
            val fragment = SlideshowFragment()
//            val bundle = Bundle()
//            bundle.putInt(Constants.EXTRA_POSITION, id)
//            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initSetListener() {
        refresh.setOnRefreshListener {
            lazyLoadData()
            refresh.isRefreshing=false
        }

        mAdapter?.setOnItemClickListener { adapter, view, position ->

            val sellId= mSellList[position].id
            Log.d("点击的id",sellId.toString())
            val userProfile=activity?.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
            val edit=userProfile?.edit()
            edit?.putLong("goodId",sellId)
            edit?.apply()
            //TODO
            val intent =Intent(activity, CommodityActivity::class.java)
            intent.putExtra("sellId",sellId)
            startActivity(intent)
        }
    }

    override fun initRecyclerView() {
        mAdapter = SellAdapter(mSellList)
        recycler?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
//        mRecycler?.layoutManager=
        recycler?.adapter = mAdapter
    }
//    private lateinit var slideshowViewModel: SlideshowViewModel
//
//    override fun onCreateView(
//            inflater: LayoutInflater,
//            container: ViewGroup?,
//            savedInstanceState: Bundle?
//    ): View? {
//        slideshowViewModel =
//                ViewModelProviders.of(this).get(SlideshowViewModel::class.java)
//        val root = inflater.inflate(R.layout.fragment_slideshow, container, false)
//        val textView: TextView = root.findViewById(R.id.text_slideshow)
//        slideshowViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
//        return root
//    }

    override fun finishTask() {
        mAdapter?.notifyDataSetChanged()
    }
}
