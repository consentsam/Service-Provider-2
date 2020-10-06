package com.laundry.user.adapter

import android.content.Context
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.laundry.user.R
import com.laundry.user.bean.HomeDetailDataResponce
import com.laundry.user.listner.RecyclerItemClickListen
import kotlinx.android.synthetic.main.add_item_dialog_box.*

import kotlinx.android.synthetic.main.item_card.view.*


class ItemListAdapter(
    val context: Context,
    val serviceList: ArrayList<HomeDetailDataResponce.Response.Items>,
    var listner: RecyclerItemClickListen.OnItemClickListener

) :
    RecyclerView.Adapter<ItemListAdapter.ViewHolder>() {
   var dryclean : String?=null
    var iron :String?=null
    var wash_iron:String?=null
    private var serviceListAdapter: ServiceListAdapter? = null
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.item_card, p0, false)
        )
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        holder.itemView.item_name_tv.text = serviceList[position].item_name
        holder.itemView.add_item_tv.setOnClickListener {
            listner.onItemClick(holder.itemView.add_item_tv,position)

            /*  val dialog = BottomSheetDialog(context)
              dialog.setContentView(R.layout.add_item_dialog_box)
              dialog.close_tv.setOnClickListener {
                  dialog.dismiss()

              }
              dialog.item_name_tv.text=serviceList[position].item_name
              //Log.d("services",services.toString())
              serviceListAdapter = ServiceListAdapter(context, serviceList[position].services,this)

              dialog.service_list_recyclerview.adapter = serviceListAdapter

              dialog.show()*/

        }


    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {





    }
}