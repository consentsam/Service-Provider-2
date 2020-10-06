package com.laundry.user.base

import androidx.fragment.app.Fragment
import com.laundry.user.utils.ErrorUtil
import com.laundry.user.utils.ProgressDialogUtils
import com.laundry.user.webservice.ApiInterface
import com.laundry.user.webservice.RetrofitUtil

abstract class BaseFragment :Fragment(){
    fun onError(it: Throwable?) {
        ProgressDialogUtils().getInstance().hideProgress()
        ErrorUtil.handlerGeneralError(context, it!!)
    }


    val apiInterface: ApiInterface by lazy {
        RetrofitUtil.createBaseApiService()
    }
}