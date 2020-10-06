package com.laundry.user.ui.activity

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

import androidx.navigation.ui.setupWithNavController
import com.laundry.user.R
import com.laundry.user.adapter.HomeDataListAdapter
import com.laundry.user.base.BaseActivity
import com.laundry.user.bean.HomeDataResponse
import com.laundry.user.utils.ErrorUtil
import com.laundry.user.utils.getGsonInstance
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.net.ConnectException


class DashboardActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

        navView.setupWithNavController(navController)

    }


    override fun inItId() {

    }

    override fun setListener() {

    }
    /*private fun getServiceList() {

        //  swipeRefresh.isRefreshing = true

        disposable = apiInterface.getHomeData(
            // access_token = sharedPreferenceWriter.getString(SharedPreferenceKey.ACCESS_TOKEN)
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {
                    // swipeRefresh.isRefreshing = false
                    onSuccessResponse(it)
                },
                {
                    // swipeRefresh.isRefreshing = false
                    onError(it)
                }
            )
    }

    private fun onSuccessResponse(response: HomeDataResponse) {
        Log.e("ServiceListResponse", getGsonInstance().toJson(response))

        serviceList = response.response
        ReturnServiceList(serviceList as ArrayList<HomeDataResponse.Response>)
       // showServiceList(response.response)
    }*/

   


}