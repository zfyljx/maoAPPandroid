package com.example.maoapp.ui.order

import android.content.Context
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.maoapp.MainActivity
import com.example.maoapp.R
import com.example.maoapp.adapter.OrderAdapter
import com.example.maoapp.base.BaseRefreshFragment
import com.example.maoapp.contract.MineOrderFragmentContract
import com.example.maoapp.model.bean.OrderModel
import com.example.maoapp.presenter.MineOrderFragmentPresenter
import com.example.maoapp.utils.ToastUtils
import kotlinx.android.synthetic.main.common_refresh_recycler.*
import kotlinx.android.synthetic.main.layout_order_card.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MineOrderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MineOrderFragment : BaseRefreshFragment<MineOrderFragmentPresenter, OrderModel>(), MineOrderFragmentContract.View {
    /**
     * 布局
     * @return int
     */
    private var mPosition :Int=0
    private var mOrdersList = ArrayList<OrderModel>()
    private var mAdapter : OrderAdapter? =null
    override fun getLayoutId(): Int = R.layout.fragment_mine_order
    override fun initPresenter() = mPresenter.attachView(this)

    override fun initInject() = fragmentComponent.inject(this)
    override fun initSetListener() {
        mAdapter?.setOnItemChildClickListener { adapter, view, position ->
            val intent= Intent(context, MainActivity::class.java)
            intent.putExtra("orderId",mOrdersList[position].id)
            startActivity(intent) }
        mAdapter?.setOnItemChildClickListener { adapter, view, position ->
            when(view){
                order_delivery_submit -> {
                    mPosition=position
                    mPresenter.updateStatus(mOrdersList[position].id)
                }
            }
        }
    }
    override fun initDatas() {
        mAdapter = OrderAdapter(mOrdersList)
        recycler?.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
//        mRecycler?.layoutManager=
        recycler?.adapter = mAdapter

        val userProfile= activity?.getSharedPreferences("userProfile", Context.MODE_PRIVATE)
        val userId=userProfile?.getLong("userId",0) as Long
        mPresenter.getOrders(userId)
    }
    override fun setOrders(orders: List<OrderModel>) {
        mOrdersList.addAll(orders)
        finishTask()
    }

    override fun showToast(tag: String) {
        ToastUtils.showToast(tag)
    }

    override fun verityStatus(result: Boolean) {
        if (result){
            mOrdersList[mPosition].status = 1
            finishTask()
        }else{
            ToastUtils.showToast("网络出错，请重试")
        }
    }
//    // TODO: Rename and change types of parameters
//    private var param1: String? = null
//    private var param2: String? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            param1 = it.getString(ARG_PARAM1)
//            param2 = it.getString(ARG_PARAM2)
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_mine_order, container, false)
//    }
//
//    companion object {
//        /**
//         * Use this factory method to create a new instance of
//         * this fragment using the provided parameters.
//         *
//         * @param param1 Parameter 1.
//         * @param param2 Parameter 2.
//         * @return A new instance of fragment MineOrderFragment.
//         */
//        // TODO: Rename and change types and number of parameters
//        @JvmStatic
//        fun newInstance(param1: String, param2: String) =
//            MineOrderFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_PARAM1, param1)
//                    putString(ARG_PARAM2, param2)
//                }
//            }
//    }

    override fun finishTask() {
        mAdapter?.notifyDataSetChanged()
    }
}
