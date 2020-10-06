package com.laundry.user.ui.activity

import android.os.Bundle
import com.laundry.user.R
import com.laundry.user.adapter.AddAddressListAdapter
import com.laundry.user.base.BaseActivity
import com.laundry.user.bean.GetAddressResponce
import com.laundry.user.shared_prefernence.SharedPreferenceKey
import com.laundry.user.shared_prefernence.SharedPreferenceWriter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_select_address.*

class SelectAddressActivity : BaseActivity() {
    private lateinit var sharedPreferenceWriter: SharedPreferenceWriter
    private var disposable: Disposable? = null
    private lateinit var addressList: List<GetAddressResponce.Response>
    private var addAddressListAdapter: AddAddressListAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_address)
        inItId()
        setListener()
    }

    override fun inItId() {
        sharedPreferenceWriter = SharedPreferenceWriter.getInstance(this)

    }

    override fun setListener() {

    }

    override fun onResume() {
        super.onResume()
        getAddressList()
    }

    private fun getAddressList() {

            disposable = apiInterface.getAddress(
                access_token = sharedPreferenceWriter.getString(SharedPreferenceKey.ACCESS_TOKEN)
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

    private fun onSuccessResponse(responce: GetAddressResponce) {




        addressList=responce.response
        showServiceList(addressList as ArrayList<GetAddressResponce.Response>)

    }

    private fun showServiceList(addressList: ArrayList<GetAddressResponce.Response>) {
        addAddressListAdapter = AddAddressListAdapter(this, addressList)

        address_recyclerview.adapter = addAddressListAdapter
    }




}