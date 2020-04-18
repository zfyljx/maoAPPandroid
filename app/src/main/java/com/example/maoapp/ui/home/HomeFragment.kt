package com.example.maoapp.ui.home

import android.util.Log
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maoapp.R
import com.example.maoapp.adapter.HomeAdapter
import com.example.maoapp.base.BaseRefreshFragment
import com.example.maoapp.contract.HomeFragmentContract
import com.example.maoapp.model.bean.ShareModelList
import com.example.maoapp.presenter.HomeFragmentPresenter
import com.example.maoapp.utils.ToastUtils
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : BaseRefreshFragment<HomeFragmentPresenter, ShareModelList.ShareModel>(), HomeFragmentContract.View {
    /**
     * 布局
     * @return int
     */

    private var mSharesList = ArrayList<ShareModelList.ShareModel>()
    private var mAdapter :HomeAdapter? =null

    override fun getLayoutId(): Int = R.layout.fragment_home
    override fun initPresenter() = mPresenter.attachView(this)

    override fun initInject() = fragmentComponent.inject(this)

    override fun lazyLoadData() = mPresenter.getShares()


    override fun initWidget() {
        home_share_query.setIconifiedByDefault(false)
        home_share_query.isSubmitButtonEnabled=true
        home_share_query.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
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
                query?.let { mPresenter.queryShares(it) }
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

    override fun setShares(shares: ShareModelList) {
       mSharesList.addAll(shares.shares)
        Log.d("GGGGGGGGGGGGGG",mSharesList.toString())
        finishTask()
    }

    override fun showToast(tag: String) {
        ToastUtils.showToast(tag)
    }

    companion object {
        fun newInstance(id: Int): HomeFragment {
            val fragment = HomeFragment()
//            val bundle = Bundle()
//            bundle.putInt(Constants.EXTRA_POSITION, id)
//            fragment.arguments = bundle
            return fragment
        }
    }

    override fun initRecyclerView() {
        mAdapter = HomeAdapter(mSharesList)
        mRecycler?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        mRecycler?.adapter = mAdapter
    }
//    private lateinit var homeViewModel: HomeViewModel
//
//    override fun onCreateView(
//            inflater: LayoutInflater,
//            container: ViewGroup?,
//            savedInstanceState: Bundle?
//    ): View? {
//        homeViewModel =
//                ViewModelProviders.of(this).get(HomeViewModel::class.java)
//        val root = inflater.inflate(R.layout.fragment_home, container, false)
//        val textView: TextView = root.findViewById(R.id.text_home)
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
//        })
//        return root
//    }

    override fun finishTask() {
        mAdapter?.notifyDataSetChanged()
    }
}
