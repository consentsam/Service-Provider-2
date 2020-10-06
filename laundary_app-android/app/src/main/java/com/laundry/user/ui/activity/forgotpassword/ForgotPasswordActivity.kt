package com.laundry.user.ui.activity.forgotpassword

import android.content.Intent

import android.os.Bundle
import android.util.Log
import android.view.View

import com.laundry.user.R
import com.laundry.user.base.BaseActivity
import com.laundry.user.bean.LoginResponse

import com.laundry.user.ui.activity.otpverify.OtpVerifyActivity
import com.laundry.user.utils.*

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : BaseActivity(),View.OnClickListener {
    private var disposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        inItId()
        setListener()
    }

    override fun inItId() {

        img_back.setOnClickListener(this)

    }

    override fun setListener() {
        inc_btn_forgot_password.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id)
        {
            R.id.inc_btn_forgot_password->
            {
                if(et_mobile_number_forgot_password.isValueEmpty("mobile number"))
                {
                    /*  val intent = Intent(this, OtpVerifyActivity::class.java)
                      intent.putExtra(IntentConstants.MOBILE_NUMBER, et_mobile_number_forgot_password.getString())
                      intent.putExtra(IntentConstants.FROM, IntentConstants.FROM_FORGOT)
                      startActivity(intent)*/
                    if (NetworkUtils.isInternetAvailable(applicationContext)) {
                        forgetPassword()
                    } else {
                        showToast(resources.getString(R.string.error_internet),applicationContext)
                    }
                }

            }
            R.id.img_back->
            {
                finish()
            }
        }
    }

    private fun forgetPassword() {



        disposable = apiInterface.forgetPassword(
            countryCode =  "977",
            mobileNumber = et_mobile_number_forgot_password.getString()
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {

                    onSuccess(it)
                },
                {

                    onError(it)
                }
            )
    }

    private fun onSuccess(response: LoginResponse) {
        Log.e("MyReponse", getGsonInstance().toJson(response))
        showToast(response.message!!,applicationContext)


        startActivity(Intent(this, OtpVerifyActivity::class.java).apply {
            putExtra("otp", response.response!!.verification_code)
            putExtra("mobile_number", et_mobile_number_forgot_password.getString())
            putExtra("access_token", response.response!!.access_token)
            putExtra("country_code", response.response!!.countryCode)
            putExtra(IntentConstants.FROM, IntentConstants.FROM_FORGOT)
        })


    }
}