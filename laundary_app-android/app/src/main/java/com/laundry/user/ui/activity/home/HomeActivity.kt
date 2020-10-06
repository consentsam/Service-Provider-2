package com.laundry.user.ui.activity.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.laundry.user.R
import com.laundry.user.base.BaseActivity
import com.laundry.user.ui.activity.login.LoginActivity
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity(),View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        inItId()
        setListener()
    }

    override fun inItId() {
    }

    override fun setListener() {
        img_sign_out.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id)
        {
            R.id.img_sign_out->
            {
                val intent = Intent(this,LoginActivity::class.java)
                startActivity(intent)
                finishAffinity()
            }
        }
    }
}