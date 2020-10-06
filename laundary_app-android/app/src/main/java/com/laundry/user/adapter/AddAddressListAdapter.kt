package com.laundry.user.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.laundry.user.R
import com.laundry.user.bean.GetAddressResponce
import kotlinx.android.synthetic.main.get_address_card.view.*


class AddAddressListAdapter(
    val context: Context,
    val serviceList: ArrayList<GetAddressResponce.Response>


) :
    RecyclerView.Adapter<AddAddressListAdapter.ViewHolder>() {

    private var lastSelectedPosition = -1
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.get_address_card, p0, false)
        )
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.itemView.address_radioBtn.setChecked(lastSelectedPosition == position);

        holder.itemView.address_radioBtn.setOnClickListener(View.OnClickListener {
            lastSelectedPosition = holder.adapterPosition
            notifyDataSetChanged()
            Toast.makeText(
                context,
                "selected offer is " + "selcted",
                Toast.LENGTH_LONG
            ).show()
        })
    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {





    }
}