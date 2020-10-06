package com.laundry.user.ui.activity.register
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.laundry.user.R
import com.laundry.user.base.BaseActivity
import com.laundry.user.bean.LoginResponse
import com.laundry.user.shared_prefernence.SharedPreferenceKey
import com.laundry.user.shared_prefernence.SharedPreferenceWriter
import com.laundry.user.ui.activity.login.LoginActivity
import com.laundry.user.ui.activity.otpverify.OtpVerifyActivity
import com.laundry.user.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_registartion.*
import kotlinx.android.synthetic.main.button_layout.*
import kotlinx.android.synthetic.main.button_layout.view.*




class RegisterActivity : BaseActivity(), View.OnClickListener, TextWatcher {
    private var isTermsAndConditionCheck = false
    private var disposable: Disposable? = null
    private lateinit var sharedPreferenceWriter: SharedPreferenceWriter
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registartion)
        inItId()
        setListener()
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun inItId() {
        inc_btn_register.btn_submit.text =getString(R.string.register)


        // for terms and condition
        val termCondition = tv_disclaimer.text.toString()
        getSpannableString(termCondition, tv_disclaimer, 6, termCondition.length, this)

        // already have an account
        val alreadyHaveAnAccount = tv_already_account.text.toString()
        getSpannableString(
            alreadyHaveAnAccount,
            tv_already_account,
            24,
            alreadyHaveAnAccount.length,
            this
        )

    }

    override fun setListener() {
        btn_submit.setOnClickListener(this)
        tv_already_account.setOnClickListener(this)
        iv_show_hide_confirm_password.setOnClickListener(this)
        iv_show_hide_password.setOnClickListener(this)

        et_password.addTextChangedListener(this);

        // listener for checkbox
        cb_terms_condition.setOnCheckedChangeListener(object :
            CompoundButton.OnCheckedChangeListener {
            override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {
                isTermsAndConditionCheck = isChecked
            }

        })

    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_submit -> {
                // for empty check only
//                sendIntent()

                if (isValidateSignUp()) {

                    if (NetworkUtils.isInternetAvailable(applicationContext)) {
                        doSignUp()
                    } else {
                        showToast(resources.getString(R.string.error_internet),applicationContext)
                    }

                    //sendIntent()

                }
            }
            R.id.tv_already_account -> {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
            R.id.iv_show_hide_password -> {
                showHidePassword(et_password, iv_show_hide_password)
            }
            R.id.iv_show_hide_confirm_password -> {
                showHidePassword(et_confirm_password_register, iv_show_hide_confirm_password)
            }
        }
    }

    private fun doSignUp() {


        disposable = apiInterface.signUp(

            country = "Saudi Arabia",
            mobileNumber = et_mobile_number_register.text.toString(),
            email = et_email_register.text.toString(),
            city = "noida",
            deviceType = "2",
            deviceToken = "q3234wqde",
            latitude = "76.00",
            longitude = "72.00",
            deviceId = "5e84c5696e7c801ffc242203",
            password = et_confirm_password_register.text.toString(),
            countryCode = "977"

            //provider
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {

                    onSignUpSuccess(it)
                },
                {

                    onError(it)
                    showToast("mobile number already exist",applicationContext)
                }

            )
    }

    private fun onSignUpSuccess(response: LoginResponse) {

        Log.e("MyReponse", " " + getGsonInstance().toJson(response))
        Log.e(" " + response.response!!.access_token, " " + response.response!!.access_token)
        sharedPreferenceWriter.writeBooleanValue(SharedPreferenceKey.IS_LOGIN, true)
        SharedPreferenceWriter.getInstance(this@RegisterActivity)
             .writeStringValue(SharedPreferenceKey.ACCESS_TOKEN, response.response!!.access_token.toString())

         SharedPreferenceWriter.getInstance(this@RegisterActivity)
             .writeStringValue(SharedPreferenceKey.EMAIL, response.response!!.email.toString())

         SharedPreferenceWriter.getInstance(this@RegisterActivity)
             .writeStringValue(SharedPreferenceKey.MOBILE_NUMBER, response.response!!.access_token.toString())


        showToast(response.message!!,applicationContext)
        clearInputs()

        startActivity(Intent(this, OtpVerifyActivity::class.java).apply {
            putExtra("otp", response.response!!.verification_code)
            // 0 = user & 1= provider
            putExtra("access_token", response.response!!.access_token)
            putExtra("country_code", response.response!!.countryCode)
            putExtra("mobile_number", response.response!!.mobileNumber)
            putExtra("country_code", response.response!!.countryCode)
            putExtra(IntentConstants.FROM, IntentConstants.FROM_SIGN_UP)
            finish()
        })

    }
    private fun clearInputs() {
        et_email_register.setText("")
        et_mobile_number_register.setText("")
        et_confirm_password_register.setText("")
        et_confirm_password_register.setText("")

    }
    private fun sendIntent() {
        val intent = Intent(this, OtpVerifyActivity::class.java)
        intent.putExtra(IntentConstants.MOBILE_NUMBER, et_mobile_number_register.getString())
        intent.putExtra(IntentConstants.FROM, IntentConstants.FROM_SIGN_UP)
        startActivity(intent)
        finish()
    }

    private fun isValidateSignUp(): Boolean {
        if (et_mobile_number_register.getString().isEmpty() && et_email_register.getString()
                .isEmpty() && et_password.getString()
                .isEmpty() && et_confirm_password_register.getString().isEmpty()
        ) {
            et_mobile_number_register.error = getString(R.string.error_mobile_number)
            et_email_register.error = getString(R.string.error_email)
            et_password.error = getString(R.string.error_password)
            et_confirm_password_register.error = getString(R.string.error_confirm_password)
            return false
        } else if (et_mobile_number_register.getString().isEmpty()) {
            et_mobile_number_register.error = getString(R.string.error_mobile_number)
            return false
        }   else if (et_password.getString().isEmpty()) {
            et_password.error = getString(R.string.error_password)
            return false
        } else if (!et_password.getString().isValidPassword) {
            et_password.error = getString(R.string.error_invalid_password)
            return false
        } else if (et_confirm_password_register.getString().isEmpty()) {
            et_confirm_password_register.error = getString(R.string.error_confirm_password)
            return false
        } else if (!et_confirm_password_register.getString().isValidPassword) {
            et_confirm_password_register.error = getString(R.string.error_invalid_password)
            return false
        } else if (et_confirm_password_register.getString() != et_password.getString()) {
            et_confirm_password_register.error = getString(R.string.error_signup_password_match)
            return false
        }
        else if(!cb_terms_condition.isChecked){
            showToast("Please check term and conditions",applicationContext)
        }
        return true
    }

    override fun afterTextChanged(s: Editable?) {
        // calculateStrength1(s.toString());
    }



    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        updatePasswordStrengthView(s.toString());
    }

    private fun updatePasswordStrengthView(password: String) {
        val progressBar = findViewById<ProgressBar>(R.id.progressBar) as ProgressBar
        val progressBar2 = findViewById<ProgressBar>(R.id.progressBar2) as ProgressBar
        val progressBar3 = findViewById<ProgressBar>(R.id.progressBar3) as ProgressBar
        val progressBar4 = findViewById<ProgressBar>(R.id.progressBar4) as ProgressBar
        val strengthView = findViewById<ProgressBar>(R.id.password_strength) as TextView
        if (TextView.VISIBLE != strengthView.visibility)
            return

        if (TextUtils.isEmpty(password)) {
            strengthView.text = ""
            progressBar.progress = 0
            return
        }

        val str = PasswordStrength.calculateStrength(password)
        strengthView.text = str.getText(this)
        strengthView.setTextColor(str.color)

        progressBar.progressDrawable.setColorFilter(str.color, android.graphics.PorterDuff.Mode.SRC_IN)
        if (str.getText(this) == "Weak") {
            progressBar.progress = 100
        } else if (str.getText(this) == "Medium") {
            progressBar2.progress =100
                progressBar.progress = 100
        } else if (str.getText(this) == "Strong") {
            progressBar3.progress = 100
            progressBar2.progress =100
                progressBar.progress = 100
            progressBar4.progress = 100
        } else {
            progressBar4.progress = 100
            progressBar3.progress = 100
            progressBar2.progress =100
            progressBar.progress = 100
        }

    }
}