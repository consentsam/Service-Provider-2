package com.laundry.user.adapter

import android.content.Context
import android.util.Log

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.laundry.user.R
import com.laundry.user.bean.AddServiceModel
import com.laundry.user.bean.HomeDetailDataResponce
import com.laundry.user.listner.RecyclerItemClickListen

import kotlinx.android.synthetic.main.item_card.view.*
import kotlinx.android.synthetic.main.servicelist_card.*
import kotlinx.android.synthetic.main.servicelist_card.view.*
import kotlinx.android.synthetic.main.servicelist_card.view.item_service_price_tv
import kotlinx.android.synthetic.main.servicelist_card.view.service_count_tv


class ServiceListAdapter(
    val context: Context,
    val serviceList: ArrayList<HomeDetailDataResponce.Response.Items.Services>,
   val addserviceList: ArrayList<AddServiceModel>,
    var listner: RecyclerItemClickListen.OnItemClickListener

) :
    RecyclerView.Adapter<ServiceListAdapter.ViewHolder>() {



    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(context)
                .inflate(R.layout.servicelist_card, p0, false)
        )
    }

    override fun getItemCount(): Int {
        return serviceList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        var count :Int=1
        holder.itemView.item_service_name_tv.text = serviceList[position].service_type_name
        holder.itemView.item_service_price_tv.text=serviceList[position].price+"SAR"
        holder.itemView.additemService_tv.setOnClickListener {
            listner.onItemClick(holder.itemView.additemService_tv,position)

        }
        holder.itemView.additemService_tv.setOnClickListener {
            holder.itemView.add_lyt.visibility=View.VISIBLE
            holder.itemView.additemService_tv.visibility=View.INVISIBLE

        }
      /*  holder.itemView.service_minus_ib.setOnClickListener {
            listner.onItemClick(holder.itemView.service_minus_ib,position)
            holder.itemView . service_count_tv.text=count.toString()
            holder.itemView .item_service_price_tv.text=(count*servicesL.get(position).price.toInt()).toString()+"SAR"
        }*/
       /* holder.itemView.service_plus_ib.setOnClickListener {
            listner.onItemClick(holder.itemView.service_plus_ib,position)
        }*/
    holder.itemView.service_minus_ib.setOnClickListener {

        listner.onItemClick1(holder.itemView.service_minus_ib,count,position)
           if (count==0){
               holder.itemView.add_lyt.visibility=View.INVISIBLE
               holder.itemView.additemService_tv.visibility=View.VISIBLE
               Log.d("servicesadd",addserviceList.toString())
               addserviceList.add(AddServiceModel(serviceList[position]._id,serviceList[position].service_type_name,serviceList[position].item_id,serviceList[position].item_name,count.toString(),(serviceList[position].price.toInt()*count).toString()))
           }
        if (count>0){
            count=count-1
            holder.itemView.service_count_tv.text=count.toString()

            holder.itemView.item_service_price_tv.text=(count*serviceList[position].price.toInt()).toString()+"SAR"
            if (count==1){
                addserviceList.remove(AddServiceModel(serviceList[position]._id,serviceList[position].service_type_name,serviceList[position].item_id,serviceList[position].item_name,count.toString(),(serviceList[position].price.toInt()*count).toString()))
                Log.d("servicesremove",addserviceList.toString())
            }
            else{
                for (item in addserviceList){
                    if (item.item_id==serviceList[position].item_id){
                        item.price=(serviceList[position].price.toInt()*count).toString()
                        item.quantity=count.toString()
                    }
                }

            }
        }
       }
        holder.itemView.service_plus_ib.setOnClickListener {
            listner.onItemClick1(holder.itemView.service_plus_ib,count,position)
                count=count+1
                holder.itemView.service_count_tv.text=count.toString()
            holder.itemView.item_service_price_tv.text=(count*serviceList[position].price.toInt()).toString()+"SAR"
            if (count==1){
                Log.d("servicesadd",addserviceList.toString())
                addserviceList.add(AddServiceModel(serviceList[position]._id,serviceList[position].service_type_name,serviceList[position].item_id,serviceList[position].item_name,count.toString(),(serviceList[position].price.toInt()*count).toString()))
            }
            else{
                for (item in addserviceList){
                    if (item.item_id==serviceList[position].item_id){
                        item.price=(serviceList[position].price.toInt()*count).toString()
                          item.quantity=count.toString()
                    }
                }

            }

        }

    }


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {





    }
}