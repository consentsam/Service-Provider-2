package com.laundry.user.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.laundry.user.R

import com.laundry.user.base.BaseActivity

import kotlinx.android.synthetic.main.activity_select_language.*
import kotlinx.android.synthetic.main.button_layout.view.*

class SelectLanguageActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_language)
        inItId()
        setListener()
    }

    override fun inItId() {
    }

    override fun setListener() {
        inc_btn_language.btn_submit.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id)
        {
            R.id.btn_submit ->
            {
                val intent = Intent(this,
                   SelectThemeModeActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}