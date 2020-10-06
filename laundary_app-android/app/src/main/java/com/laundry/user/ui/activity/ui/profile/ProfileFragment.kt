package com.laundry.user.ui.activity.ui.profile

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.laundry.user.R
import com.laundry.user.shared_prefernence.SharedPreferenceKey
import com.laundry.user.shared_prefernence.SharedPreferenceWriter

import com.laundry.user.ui.activity.login.LoginActivity
import com.laundry.user.ui.activity.profile.EditProfileActivity

import kotlinx.android.synthetic.main.profile_fragment.*

class ProfileFragment : Fragment() {
  private lateinit var sharedPreferenceWriter: SharedPreferenceWriter


    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       val root= inflater.inflate(R.layout.profile_fragment, container, false)
        val context: Context = container!!.context

        sharedPreferenceWriter = SharedPreferenceWriter.getInstance(context)


        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
        profile_number_tv.setText(sharedPreferenceWriter.getString(SharedPreferenceKey.MOBILE_NUMBER))
       // profile_name_tv.setText(sharedPreferenceWriter.getString(SharedPreferenceKey.FIRST_NAME))
        profile_email_tv.setText(sharedPreferenceWriter.getString(SharedPreferenceKey.EMAIL))
        edit_profile_tv.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            startActivity(intent)

            sharedPreferenceWriter.clearPreferenceValues()

        }
        edit_profile_tv.setOnClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(intent)

        }
        my_account_det_iv.setOnClickListener {
            my_account_det_iv.setImageResource(R.drawable.setting_right_arrow)
            my_help_det_iv.setImageResource(R.drawable.setting_drop_down)
            my_account_det_lly.visibility=View.VISIBLE
            my_account_det_tv.visibility=View.GONE
            my_help_det_lly.visibility=View.GONE
            my_help_det_tv.visibility=View.VISIBLE
        }
        my_help_det_iv.setOnClickListener {
            my_help_det_iv.setImageResource(R.drawable.setting_right_arrow)
            my_account_det_iv.setImageResource(R.drawable.setting_drop_down)
            my_help_det_lly.visibility=View.VISIBLE
            my_help_det_tv.visibility=View.GONE
            my_account_det_lly.visibility=View.GONE
            my_account_det_tv.visibility=View.VISIBLE
        }
    }

}