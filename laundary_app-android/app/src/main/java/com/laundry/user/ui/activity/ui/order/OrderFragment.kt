package com.laundry.user.ui.activity.ui.order

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.laundry.user.R
import com.laundry.user.ui.activity.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_order.*
import kotlinx.android.synthetic.main.profile_fragment.*


class OrderFragment : Fragment() {

    private lateinit var orderViewModel: OrderViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        orderViewModel =
            ViewModelProviders.of(this).get(OrderViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_order, container, false)
        val past_tv: TextView = root.findViewById(R.id.past_tv)

        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        past_tv.setOnClickListener {

            order_list_recyclerview.visibility=View.GONE



        }
        ongoing_tv.setOnClickListener {

            order_list_recyclerview.visibility=View.VISIBLE



        }
    }
}