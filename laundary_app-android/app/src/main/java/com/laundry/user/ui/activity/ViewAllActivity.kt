package com.laundry.user.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View

import com.laundry.user.R
import com.laundry.user.adapter.AllDataListAdapter

import com.laundry.user.base.BaseActivity
import com.laundry.user.bean.HomeDataResponse
import com.laundry.user.shared_prefernence.SharedPreferenceKey
import com.laundry.user.shared_prefernence.SharedPreferenceWriter
import com.laundry.user.utils.ErrorUtil
import com.laundry.user.utils.getGsonInstance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_view_all.*
import java.net.ConnectException

class ViewAllActivity :  BaseActivity(), View.OnClickListener {
    private var disposable: Disposable? = null
    private var homeDataListAdapter: AllDataListAdapter? = null
    private lateinit var sharedPreferenceWriter: SharedPreferenceWriter
    private lateinit var serviceList: List<HomeDataResponse.Response.Shops>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_all)
        inItId()
    }
    override fun onResume() {
        super.onResume()
        if (NetworkUtils.isInternetAvailable(this))
            getServiceList()
        else
            ErrorUtil.handlerGeneralError(this, ConnectException())
    }

    override fun inItId() {
        sharedPreferenceWriter = SharedPreferenceWriter.getInstance(this)

    }

    override fun setListener() {
        btnBack.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {

            //back button
            R.id.btnBack -> {
                onBackPressed()
            }
        }
    }
    /**
     * Api call to get the service list
     */
    private fun getServiceList() {



        disposable = apiInterface.getHomeData(
           access_token = sharedPreferenceWriter.getString(SharedPreferenceKey.ACCESS_TOKEN)
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {

                    onSuccessResponse(it)
                },
                {

                    onError(it)
                }
            )
    }

    private fun onSuccessResponse(response: HomeDataResponse) {
        Log.e("ServiceListResponse1", getGsonInstance().toJson(response))

       serviceList = response.response!!.shops
        showServiceList(response.response.shops)
    }

    /**
     * set the service list in adapter to recyclerview
     */
    private fun showServiceList(serviceList: List<HomeDataResponse.Response.Shops>) {

        homeDataListAdapter = AllDataListAdapter(this, serviceList)
        //service_list_recyclerview.layoutManager = LinearLayoutManager(this)
        service_list_recyclerview.adapter = homeDataListAdapter
    }
    override fun onBackPressed() {

        //save the selected services in shared prefs to get in the service details screen
        /*   if (serviceListAdapter != null)
               sharedPreferenceWriter
                   .writeStringArrayValue(
                       SharedPreferenceKey.SERVICE_LIST,
                       serviceListAdapter!!.selectedServiceList
                   )*/

//        if (isEdit) {
//            finish()
//        } else {

       /* if (serviceList != null && serviceList.size > 0) {
            SharedPreferenceWriter.getInstance(this)
                .writeBooleanValue(SharedPreferenceKey.IS_SERVICE_LIST_SAVED, true)
        } else {
            SharedPreferenceWriter.getInstance(this)
                .writeBooleanValue(SharedPreferenceKey.IS_SERVICE_LIST_SAVED, false)
        }*/

        // back to service detail screen
        finish()
//        }
    }
}