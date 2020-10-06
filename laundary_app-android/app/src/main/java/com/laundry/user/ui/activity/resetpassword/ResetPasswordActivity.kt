package com.laundry.user.ui.activity.resetpassword

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View


import com.laundry.user.R
import com.laundry.user.base.BaseActivity
import com.laundry.user.bean.LoginResponse


import com.laundry.user.ui.activity.login.LoginActivity

import com.laundry.user.utils.*

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_forgot_password.img_back

import kotlinx.android.synthetic.main.activity_reset_password.*


class ResetPasswordActivity : BaseActivity(),View.OnClickListener {
    private var disposable: Disposable? = null
    private var access_token: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)
        access_token = intent.extras?.getString("access_token")
        inItId()
        setListener()
    }

    override fun inItId() {


    }

    override fun setListener() {
        img_back.setOnClickListener(this)
        inc_btn_reset_password.setOnClickListener(this)
        iv_show_hide_password_reset.setOnClickListener(this)
        iv_show_hide_repassword_reset.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id)
        {
            R.id.img_back->
            {
                finish()
            }
            R.id. iv_show_hide_password_reset -> {
                showHidePassword(et_password_reset, iv_show_hide_password_reset)
            }
            R.id. iv_show_hide_repassword_reset -> {
                showHidePassword(et_confirm_password_reset, iv_show_hide_repassword_reset )
            }
            R.id. inc_btn_reset_password->
            {
//                step1
//                check password empty or not  on password and confirm password
                if( et_password_reset.isValueEmpty("new password")&&
                    et_confirm_password_reset.isValueEmpty("confirm password")
                )
                {

//                    step2
//                    check password length verify the condition
                    if(checkPassword(et_password_reset.getString(),"new password") &&
                        checkPassword(et_confirm_password_reset.getString(),"confirm password"))
                    {

//                        step3
//                            check new and confirm password
                        if(matchTwoPassword(et_password_reset.getString(),
                                et_confirm_password_reset.getString(),
                                "New password and confirm password does'nt match"))
                        {
                            /* val intent = Intent (this, LoginActivity::class.java)
                             startActivity(intent)
                             finish()*/
                            if (NetworkUtils.isInternetAvailable(applicationContext)) {
                                resetPassword()
                            } else {
                                showToast(resources.getString(R.string.error_internet),applicationContext)
                            }
                        }

                    }

                }
            }

        }
    }

    private fun resetPassword() {


        disposable = apiInterface.resetPassword(
            access_token = access_token!!,
            password = et_password_reset.getString()

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
        showToast(response.message,applicationContext)

        val intent = Intent(this@ResetPasswordActivity, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}