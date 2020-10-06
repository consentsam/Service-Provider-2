package com.laundry.user.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.laundry.user.R

import com.laundry.user.base.BaseActivity
import com.laundry.user.ui.activity.register.RegisterActivity
import kotlinx.android.synthetic.main.activity_select_theme_mode.*
import kotlinx.android.synthetic.main.button_layout.view.*

class SelectThemeModeActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_theme_mode)
        inItId()
        setListener()
    }

    override fun inItId() {
    }

    override fun setListener() {
        inc_btn_theme.btn_submit.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id)
        {
            R.id.btn_submit->
            {
                val intent = Intent(this,
                  RegisterActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}