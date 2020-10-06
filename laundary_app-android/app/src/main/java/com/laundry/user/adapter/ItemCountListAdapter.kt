package com.laundry.user.adapter

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.laundry.user.R
import com.laundry.user.bean.HomeDetailDataResponce
import com.laundry.user.listner.RecyclerItemClickListen

import kotlinx.android.synthetic.main.item_card.view.*
import kotlinx.android.synthetic.main.item_card.view.item_name_tv
import kotlinx.android.synthetic.main.item_count_card.view.*


class ItemCountListAdapter(
    val context: Context,
    val serviceList: ArrayList<HomeDetailDataResponce.Response.Items>,
    var listner: RecyclerItemClickListen.OnItemClickListener

) :
    RecyclerView.Adapter<ItemCountListAdapter.ViewHolder>() {



    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_count_card, p0, false)
        )
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.itemView.item_name_tv.text = serviceList[position].item_name
        holder.itemView.add_item_tv.setOnClickListener {
            listner.onItemClick(holder.itemView,position)
        }

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {





    }
}