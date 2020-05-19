package com.example.maoapp.ui.slideshow

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.appcompat.widget.SearchView
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
import kotlinx.android.synthetic.main.fragment_slideshow.*

class SlideshowFragment : BaseRefreshFragment<SellFragmentPresenter, SellModel>(), SellFragmentContract.View {

    private var mSellList=ArrayList<SellModel>()
    private var mAdapter:SellAdapter?=null
    private var mSellsList=ArrayList<SellModel>()

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
        mSellsList.clear()
        mSellsList.addAll(sells)
        mSellList.addAll(filterList(0,sells))
        finishTask()
    }

    override fun showToast(tag: String) {
        ToastUtils.showToast(tag)
    }

    override fun setQuerySells(sells: List<SellModel>) {
        mSellList.clear()
        mSellList.addAll(sells)
        finishTask()
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


        good_cat.setOnClickListener {
            mSellList.addAll(filterList(0,mSellsList))
            finishTask()
            good_cat_image.visibility=View.VISIBLE
            good_dog_image.visibility=View.INVISIBLE
            good_good_image.visibility=View.INVISIBLE

        }

        good_dog.setOnClickListener {
            mSellList.addAll(filterList(1,mSellsList))
            finishTask()
            good_cat_image.visibility=View.INVISIBLE
            good_dog_image.visibility=View.VISIBLE
            good_good_image.visibility=View.INVISIBLE

        }

        good_good.setOnClickListener {
            mSellList.addAll(filterList(2,mSellsList))
            finishTask()
            good_cat_image.visibility=View.INVISIBLE
            good_dog_image.visibility=View.INVISIBLE
            good_good_image.visibility=View.VISIBLE

        }

    }
    override fun initWidget() {
        good_search.setIconifiedByDefault(false)
        good_search.isSubmitButtonEnabled=true
        good_search.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            /**
             * Called when the user submits the query. This could be due to a key press on the
             * keyboard or due to pressing a submit button.
             * The listener can override the standard behavior by returning true
             * to indicate that it has handled the submit request. Otherwise return false to
             * let the SearchView handle the submission by launching any associated intent.
             *
             * @param query the query text that is to be submitted
             *
             * @return true if the query has been handled by the listener, false to let the
             * SearchView perform the default action.
             */
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let { mPresenter.querySells(it) }

                return false
            }

            /**
             * Called when the query text is changed by the user.
             *
             * @param newText the new content of the query text field.
             *
             * @return false if the SearchView should perform the default action of showing any
             * suggestions if available, true if the action was handled by the listener.
             */
            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
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

    private fun filterList(number:Int,mmList: List<SellModel>):List<SellModel>{
      return  mmList.filter { it.classification == number }

    }
}
