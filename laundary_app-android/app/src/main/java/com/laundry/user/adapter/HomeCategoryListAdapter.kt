package com.laundry.user.adapter

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.laundry.user.Constants
import com.laundry.user.R
import com.laundry.user.bean.HomeDataResponse

import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.category_card.view.*

import kotlinx.android.synthetic.main.laundry_card.view.*


class HomeCategoryListAdapter(
    val context: Context,
    val serviceList: List<HomeDataResponse.Response.Categories>

) :
    RecyclerView.Adapter<HomeCategoryListAdapter.ViewHolder>() {

    //list of selected services
    var selectedServiceList: ArrayList<String> = ArrayList()


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.category_card, p0, false)
        )
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.itemView.category_name_tv.text =serviceList[position].name


       selectedServiceList.add(serviceList[position]._id)

               Glide.with(context)
           .load(Constants.BASE_URL+serviceList[position].image)
           .placeholder(R.drawable.top_wear)

           .into(holder.itemView.category_image_iv)

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {





    }
}