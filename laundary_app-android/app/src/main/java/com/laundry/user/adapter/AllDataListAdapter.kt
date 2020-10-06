package com.laundry.user.adapter

import android.content.Context
import android.content.Intent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.laundry.user.R
import com.laundry.user.bean.HomeDataResponse
import com.laundry.user.ui.activity.LaundryDetailActivity



import kotlinx.android.synthetic.main.laundry_card.view.*


class AllDataListAdapter(
    val context: Context,
    val serviceList: List<HomeDataResponse.Response.Shops>

) :
    RecyclerView.Adapter<AllDataListAdapter.ViewHolder>() {


    var selectedServiceList: ArrayList<String>?= ArrayList()


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.laundry_card, p0, false)
        )
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
   var name:String=""

        holder.itemView.home_laundry_name_tv.text = serviceList[position].laudary_name
        holder.itemView.home_laundry_address_tv.text = serviceList[position].address

        holder.itemView.home_laundry_minorder_tv.text ="Min Order :- "+ serviceList[position].min_order
        holder.itemView.home_laundry_rating_tv.text =serviceList[position].rate
        Glide.with(context)
            .load("http://15.207.1.62:3536/"+serviceList[position].profile_image)
            .placeholder(R.drawable.home_image)

            .into(holder.itemView.home_laundary_iv)


        holder.itemView.setOnClickListener {

            context.startActivity(
                Intent(context, LaundryDetailActivity::class.java)
                    .putExtra("id",serviceList[position]._id)
                    .putExtra("image",serviceList[position].profile_image)
                    .putExtra("rate",serviceList[position].rate)
                    .putExtra("name",name)
                    .putExtra("address",serviceList[position].address)
            )
        }
    selectedServiceList?.add(serviceList[position]._id)

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {





    }
}