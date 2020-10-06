package com.laundry.user.ui.activity.otpverify

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.laundry.user.R
import com.laundry.user.base.BaseActivity
import com.laundry.user.bean.LoginResponse

import com.laundry.user.ui.activity.home.HomeActivity
import com.laundry.user.ui.activity.profile.ProfileCreateActivity
import com.laundry.user.ui.activity.resetpassword.ResetPasswordActivity
import com.laundry.user.utils.*

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_otp_verify.*

import kotlinx.android.synthetic.main.button_layout.view.*

class OtpVerifyActivity : BaseActivity(), View.OnClickListener {
    private var from: String? = null

    private var disposable: Disposable? = null
    private var access_token: String? = null
    private var mobile_number: String? = null
    private var countryCode: String? = null
    private var otp: String? = null

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp_verify)
        access_token = intent.extras?.getString("access_token")
        countryCode = intent.extras?.getString("country_code")
        mobile_number = intent.extras?.getString("mobile_number")
        otp= intent.extras?.getString("otp")
       // showToast(otp.toString(),applicationContext)
        inItId()
        setListener()
        getValueFromIntent()
    }


    override fun inItId() {
        inc_btn_verify.btn_submit.text = getString(R.string.verify)
        // already have an account
        val resendMessage = tv_resend_otp.text.toString()
        getSpannableString(
            resendMessage,
            tv_resend_otp,
            23,
            resendMessage.length,
            this
        )

    }

    override fun setListener() {
        inc_btn_verify.btn_submit.setOnClickListener(this)
        otpText()
    }
    private fun getValueFromIntent() {
        from = intent.getStringExtra(IntentConstants.FROM)
        mobile_number = intent.getStringExtra(IntentConstants.MOBILE_NUMBER)
    }
    private fun otpText() {
        editTextFirstOtp.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (editTextFirstOtp.text!!.length == 1) {
                    editTextSecondOtp.requestFocus()
                }

            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
        editTextSecondOtp.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (editTextSecondOtp.text!!.length == 1) {
                    editTextThirdOtp.requestFocus()
                }
                if (editTextSecondOtp.text!!.isEmpty()) {
                    editTextFirstOtp.requestFocus()
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
        editTextThirdOtp.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (editTextThirdOtp.text!!.length == 1) {
                    editTextFourOtp.requestFocus()
                }
                if (editTextThirdOtp.text!!.isEmpty()) {
                    editTextSecondOtp.requestFocus()
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
        editTextFourOtp.addTextChangedListener(object : TextWatcher {
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (editTextFourOtp.text!!.length == 1) {
                    hideKeyboard()
                }
                if (editTextFourOtp.text!!.isEmpty()) {
                    editTextThirdOtp.requestFocus()
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun afterTextChanged(s: Editable) {
            }
        })
    }
    override fun onClick(p0: View?) {
        when(p0?.id)
        {


            R.id.btn_submit->
            {
                // sendIntent()
                if (isValidInputs()) {
                    if (NetworkUtils.isInternetAvailable(applicationContext)) {
                        verifyOtp()
                    } else {
                        showToast(resources.getString(R.string.error_internet),applicationContext)
                    }
                }
            }
        }
    }

    private fun isValidInputs(): Boolean {

        if( editTextFirstOtp.text.isEmpty() || editTextSecondOtp.text.isEmpty()
            || editTextThirdOtp.text.isEmpty() ||editTextFourOtp.text.isEmpty()
        ) {

            showToast(resources.getString(R.string.error_otp),applicationContext)
            return false
        }
        return true
    }

    private fun verifyOtp() {
//        showProgressBar(this)


        disposable = apiInterface.verifyOtp(
            access_token = access_token!!,
            verification_code = otp!!/*editTextFirstOtp.text.toString() +editTextSecondOtp.text.toString() +
                    editTextThirdOtp.text.toString()+ editTextFourOtp.text.toString()*/

        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {

                    onOtpSuccess(it)
                },
                {

                    onError(it)
                }

            )
    }
    private fun onOtpSuccess(response: LoginResponse) {

        Log.e("MyReponse", getGsonInstance().toJson(response))
        showToast(response.message!!,applicationContext)






        if (from != null && from.equals(IntentConstants.FROM_FORGOT)) {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            intent.putExtra("access_token",response.response!!.access_token)
            startActivity(intent)
            finish()
        } else if (from != null && from.equals(IntentConstants.FROM_SIGN_UP)) {
            val intent = Intent(this, ProfileCreateActivity::class.java)

            intent.putExtra("access_token",response.response!!.access_token)
            intent.putExtra("mobile_number",response.response!!.mobileNumber)
            intent.putExtra("email",response.response!!.email)
            startActivity(intent)
            finish()
        } else {
            // else block for login
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }

    private fun sendIntent() {
        if (from != null && from.equals(IntentConstants.FROM_FORGOT)) {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            intent.putExtra(IntentConstants.MOBILE_NUMBER, mobile_number)
            startActivity(intent)
            finish()
        } else if (from != null && from.equals(IntentConstants.FROM_SIGN_UP)) {
            val intent = Intent(this, ProfileCreateActivity::class.java).apply {
                intent.putExtra(IntentConstants.MOBILE_NUMBER, mobile_number)
            }
            startActivity(intent)
            finish()
        } else {
            // else block for login
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }
}