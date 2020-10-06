package com.laundry.user.adapter

import android.content.Context

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.laundry.user.R
import com.laundry.user.listner.RecyclerItemClickListen

import kotlinx.android.synthetic.main.categoty_type_card.view.*
import kotlinx.android.synthetic.main.item_card.view.*


class CategoryListAdapter(
    val context: Context,
    val serviceList: ArrayList<String>,
    var listner: RecyclerItemClickListen.OnItemClickListener

) :
    RecyclerView.Adapter<CategoryListAdapter.ViewHolder>() {

    //list of selected services
   /* var selectedServiceList: ArrayList<String>?= ArrayList()
    var savedServiceList: ArrayList<String>? = ArrayList()*/

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.categoty_type_card, p0, false)
        )
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        //setting the text on checkbox from the service list
        holder.itemView.category_tv.text = serviceList[position]
        holder.itemView.category_lyt.setOnClickListener {
            holder.itemView .category_view.visibility=View.VISIBLE
            listner.onItemClick(holder.itemView,position)
        }

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {





    }
}