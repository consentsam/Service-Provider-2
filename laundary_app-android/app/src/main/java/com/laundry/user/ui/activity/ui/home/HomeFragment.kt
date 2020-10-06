package com.laundry.user.ui.activity.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager

import com.laundry.user.R
import com.laundry.user.adapter.HomeCategoryListAdapter
import com.laundry.user.adapter.HomeDataListAdapter
import com.laundry.user.base.BaseFragment
import com.laundry.user.bean.HomeDataResponse
import com.laundry.user.shared_prefernence.SharedPreferenceKey

import com.laundry.user.shared_prefernence.SharedPreferenceWriter
import com.laundry.user.ui.activity.ViewAllActivity
import com.laundry.user.utils.getGsonInstance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.fragment_home.*


class HomeFragment : BaseFragment() {
    private lateinit var sharedPreferenceWriter: SharedPreferenceWriter
    private lateinit var homeViewModel: HomeViewModel
    private var disposable: Disposable? = null
    private var homeDataListAdapter: HomeDataListAdapter? = null
    private var homeCategoryListAdapter: HomeCategoryListAdapter? = null
    private lateinit var serviceList: List<HomeDataResponse.Response.Shops>
    private lateinit var categoryList: List<HomeDataResponse.Response.Categories>
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val context: Context = container!!.context

        sharedPreferenceWriter = SharedPreferenceWriter.getInstance(context)
      getServiceList(context)

        return root
    }

    private fun getServiceList(context: Context) {
        disposable = apiInterface.getHomeData(
            access_token = sharedPreferenceWriter.getString(SharedPreferenceKey.ACCESS_TOKEN)
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    // swipeRefresh.isRefreshing = false
                    onSuccessResponse(it,context)
                },
                {
                    // swipeRefresh.isRefreshing = false
                    onError(it)
                }
            )
    }

    private fun onSuccessResponse(response: HomeDataResponse?, context: Context) {
        Log.e("ServiceListResponse", getGsonInstance().toJson(response))

        serviceList = response!!.response!!.shops

        categoryList= response.response?.categories!!
        showServiceList(response!!.response!!.shops,response.response.categories!!,context)
    }

    private fun showServiceList(
        shops: ArrayList<HomeDataResponse.Response.Shops>,
        categorys: ArrayList<HomeDataResponse.Response.Categories>,
        context: Context
    ) {
        homeDataListAdapter = HomeDataListAdapter(context, shops)
        homeCategoryListAdapter= HomeCategoryListAdapter(context,categorys)
      //  rvlaundry.layoutManager = LinearLayoutManager(context)
        rvlaundry.adapter = homeDataListAdapter
      //  rvCategory.layoutManager = LinearLayoutManager(context)
        rvCategory.adapter = homeCategoryListAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)



        viewall_tv.setOnClickListener {
            val intent = Intent(activity, ViewAllActivity::class.java)
            startActivity(intent)

        }


    }


    }

