package com.laundry.user.ui.activity.login

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.google.firebase.iid.FirebaseInstanceId

import com.laundry.user.R

import com.laundry.user.base.BaseActivity
import com.laundry.user.bean.LoginResponse
import com.laundry.user.shared_prefernence.SharedPreferenceKey
import com.laundry.user.shared_prefernence.SharedPreferenceWriter
import com.laundry.user.ui.activity.DashboardActivity
import com.laundry.user.ui.activity.register.RegisterActivity
import com.laundry.user.ui.activity.forgotpassword.ForgotPasswordActivity
import com.laundry.user.ui.activity.otpverify.OtpVerifyActivity
import com.laundry.user.ui.activity.profile.ProfileCreateActivity
import com.laundry.user.utils.*

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.button_layout.view.*

class LoginActivity : BaseActivity(), View.OnClickListener {
    private var disposable: Disposable? = null
    private lateinit var sharedPreferenceWriter: SharedPreferenceWriter

    @RequiresApi(Build.VERSION_CODES.M)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        sharedPreferenceWriter = SharedPreferenceWriter.getInstance(this)
        inItId()
        setListener()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun inItId() {
        // for dont have account
        val dontHaveAccount = tv_not_account.text.toString()
        getSpannableString(dontHaveAccount, tv_not_account, 23, dontHaveAccount.length, this)

        inc_btn_login.btn_submit.text = getString(R.string.login)

    }

    override fun setListener() {
        inc_btn_login.btn_submit.setOnClickListener(this)
        tv_not_account.setOnClickListener(this)
        iv_show_hide_password_login.setOnClickListener(this)
        tv_forgot_password_login.setOnClickListener(this)


    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_submit -> {


                if (NetworkUtils.isInternetAvailable(this)) {
                    if (isValidInputs()) {
                        login()
                    }

                } else {
                    showToast(resources.getString(R.string.error_internet), applicationContext)
                }
            }
            R.id.tv_not_account -> {
                val intent = Intent(this, RegisterActivity::class.java)
                startActivity(intent)
            }
            R.id.iv_show_hide_password_login -> {
                showHidePassword(et_password_login, iv_show_hide_password_login)
            }
            R.id.tv_forgot_password_login -> {
                val intent = Intent(this, ForgotPasswordActivity::class.java)
                startActivity(intent)
            }

        }
    }

    private fun isValidInputs(): Boolean {

        if (et_mobile_number_login.text.isEmpty() && et_password_login.text.isEmpty()) {
            et_mobile_number_login.error = resources.getString(R.string.enter_mobile_number)
            et_password_login.error = resources.getString(R.string.error_password)
            et_mobile_number_login.requestFocus()
            return false
        }
        if (et_mobile_number_login.getString().isEmpty()) {
            et_mobile_number_login.error = resources.getString(R.string.enter_mobile_number)
            et_mobile_number_login.requestFocus()
            return false
        }
        if (!et_mobile_number_login.getString().isValidMobile) {
            et_mobile_number_login.error = resources.getString(R.string.error_valid_mobile)
            et_mobile_number_login.requestFocus(et_mobile_number_login.length())
            return false
        }
        if (et_password_login.getString().isEmpty()) {
            et_password_login.error = resources.getString(R.string.error_password)
            et_password_login.requestFocus()
            return false
        }
        if (!et_password_login.getString().isValidPassword) {
            et_password_login.error = resources.getString(R.string.error_invalid_password)
            et_password_login.requestFocus(et_password_login.length())
            return false
        }
        return true
    }


    private fun login() {

        FirebaseInstanceId.getInstance().instanceId.addOnCompleteListener {
            if (it.isSuccessful) {
                val deviceToken = it.result?.token
                Log.e("deviceTokenn>>>>", deviceToken)
                disposable = apiInterface.login(
                    mobileNumber = et_mobile_number_login.getString(),
                    deviceType = "2",
                    deviceToken = deviceToken!!,
                    latitude = "72.00",
                    longitude = "76.00",
                    password = et_password_login.getString(),
                    countryCode = "977"
                ).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                        {

                            onLoginSuccess(it)
                        },
                        {

                            onError(it)
                            showToast("Invalid credentials", applicationContext)
                        }
                    )
            }
        }


    }

    private fun onLoginSuccess(response: LoginResponse) {

        showToast(response.message, applicationContext)
        if (response.message.equals("Please verify otp")) {
            startActivity(Intent(this, OtpVerifyActivity::class.java).apply {
                putExtra("otp", response.response!!.verification_code)
                // 0 = user & 1= provider
                putExtra("access_token", response.response!!.access_token)
                putExtra("country_code", response.response!!.countryCode)
                putExtra("mobile_number", response.response!!.mobileNumber)
                putExtra("country_code", response.response!!.countryCode)
                putExtra(IntentConstants.FROM, IntentConstants.FROM_SIGN_UP)
                startActivity(intent)
                finish()
            }
            )
        } else {

            proceedFurther(response)
        }
    }

    private fun proceedFurther(response: LoginResponse) {


        if (response.response.profile_created == 0) {
            startActivity(Intent(this, ProfileCreateActivity::class.java).apply {
                putExtra("access_token", response.response!!.access_token)
                saveProviderData(response.response)
                finish()
            })

        } else {
            sharedPreferenceWriter.writeBooleanValue(SharedPreferenceKey.IS_LOGIN, true)
            saveProviderData(response.response)

            val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
            startActivity(intent)
            finish()

        }
    }

    private fun saveProviderData(response: LoginResponse.Response) {

        sharedPreferenceWriter.writeBooleanValue(SharedPreferenceKey.IS_USER, false)

        sharedPreferenceWriter.writeBooleanValue(SharedPreferenceKey.IS_LOGIN, true)
        if (response.countryCode != null)
            sharedPreferenceWriter.writeStringValue(
                SharedPreferenceKey.COUNTRY_CODE,
                response.countryCode
            )
        if (response.mobileNumber != null)
            sharedPreferenceWriter.writeStringValue(
                SharedPreferenceKey.MOBILE_NUMBER,
                response.mobileNumber
            )
        if (response.email != null)
            sharedPreferenceWriter.writeStringValue(SharedPreferenceKey.EMAIL, response.email)


        if (response.profile_image != null)
            sharedPreferenceWriter.writeStringValue(
                SharedPreferenceKey.PROFILE_IMAGES,
                response.profile_image
            )



        sharedPreferenceWriter.writeStringValue(
            SharedPreferenceKey.ACCESS_TOKEN,
            response.access_token
        )
    }
}