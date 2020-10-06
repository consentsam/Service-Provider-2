package com.laundry.user.ui.activity

import NetworkUtils
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.laundry.user.R
import com.laundry.user.adapter.CategoryListAdapter
import com.laundry.user.adapter.ItemListAdapter
import com.laundry.user.adapter.ServiceListAdapter
import com.laundry.user.base.BaseActivity
import com.laundry.user.bean.AddServiceModel
import com.laundry.user.bean.HomeDetailDataResponce
import com.laundry.user.listner.RecyclerItemClickListen
import com.laundry.user.shared_prefernence.SharedPreferenceKey
import com.laundry.user.shared_prefernence.SharedPreferenceWriter
import com.laundry.user.utils.ErrorUtil
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_laundry_detail.*
import kotlinx.android.synthetic.main.activity_laundry_detail.home_laundry_name_tv
import kotlinx.android.synthetic.main.add_item_dialog_box.*
import kotlinx.android.synthetic.main.servicelist_card.*
import kotlinx.android.synthetic.main.servicelist_card.view.*
import java.net.ConnectException


class LaundryDetailActivity : BaseActivity(),RecyclerItemClickListen.OnItemClickListener,View.OnClickListener{
    var id: String = ""
    var image: String = ""
    var rate:String =""
    var name: String = ""
    var address: String = ""
    var service_type_id :String =""
    var service_name : String =" "
    var item_id :String =""
    var item_name : String =" "
    var quantity :String =""
    var price : String =" "
    val servicelist = ArrayList<AddServiceModel>()

    private var disposable: Disposable? = null
    private var categoryListAdapter: CategoryListAdapter? = null
    private var itemListAdapter: ItemListAdapter? = null
    private var serviceListAdapter: ServiceListAdapter? = null
//    private var itemcountListAdapter: ItemCountListAdapter? = null
    private lateinit var sharedPreferenceWriter: SharedPreferenceWriter
    private lateinit var itemList: ArrayList<HomeDetailDataResponce.Response>
  // val serviceList: ArrayList<HomeDetailDataResponce.Response.Items>()
    val categoryList = ArrayList<String>()
     var items = ArrayList<HomeDetailDataResponce.Response.Items>()
    var services = ArrayList<HomeDetailDataResponce.Response.Items.Services>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_laundry_detail)
        id = intent.getStringExtra("id")?:""
       image = intent.getStringExtra("image")?: ""
        rate = intent.getStringExtra("rate")?:" "
        name = intent.getStringExtra("name")?: ""
        address = intent.getStringExtra("address")?:""
        //iv_service
      Glide.with(this)
            .load("http://15.207.1.62:3536/"+image)
            .placeholder(R.drawable.home_image)

            .into(home_detail_image);
        home_laundry_name_tv.text=name
        home_detail_rating_tv.text=rate
        inItId()
        setListener()
    }

    override fun onResume() {
        super.onResume()

    }

    override fun inItId() {
        sharedPreferenceWriter = SharedPreferenceWriter.getInstance(this)
        if (NetworkUtils.isInternetAvailable(this))
            getDataDetail()
        else
            ErrorUtil.handlerGeneralError(this, ConnectException())
    }

    private fun getDataDetail() {

        disposable = apiInterface.getHomeDetailData(
            access_token = sharedPreferenceWriter.getString(SharedPreferenceKey.ACCESS_TOKEN),
                seller_id=id
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {

                    onSuccessResponse(it)
                },
                {

                    onError(it)
                }
            )
    }

    private fun onSuccessResponse(responce: HomeDetailDataResponce?) {
        for (item in responce!!.response){

            categoryList.add(item.category_name)
        }

        showCategoryList(categoryList)
        showItem(responce.response.get(0).items)
      //  serviceList=responce.response.
        itemList=responce.response
        Log.d("responce",responce.response.toString())

    }

    private fun showItem(items: ArrayList<HomeDetailDataResponce.Response.Items>) {
        Log.d("item",items.toString())
        itemListAdapter = ItemListAdapter(this, items,this)
        this.items =items
        //service_list_recyclerview.layoutManager = LinearLayoutManager(this)
        item_list_recyclerview.adapter = itemListAdapter
    }

    private fun showCategoryList(categoryList: ArrayList<String>) {
        categoryListAdapter = CategoryListAdapter(this, categoryList,this)
        //service_list_recyclerview.layoutManager = LinearLayoutManager(this)
        category_list_recyclerview.adapter = categoryListAdapter
    }

    override fun setListener() {
        proceed_to_payment.setOnClickListener(this) }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.proceed_to_payment -> {
                Log.d("services_",servicelist.toString())

                startActivity(Intent(this, SchedulePickupActivity::class.java).apply {
                    putExtra("name", name)
                    putExtra("id", id)
                    putParcelableArrayListExtra("servicelist", servicelist)


                    // 0 = user & 1= provider

                    startActivity(intent)
                    finish()
                }
                )
            }
        }}

    override fun onItemClick(view: View, position: Int) {

        when(view?.id) {
            R.id.add_item_tv -> {
                OpenBottomDialog(items.get(position).services,items.get(position).item_name)
                services=items.get(position).services
               // Toast.makeText(this,"click",Toast.LENGTH_SHORT).show()
                service_type_id=items.get(position).services.get(0).service_type
                service_name=items.get(position).services.get(0).service_type_name
                item_id=items.get(position).services.get(0).item_id
                item_name=items.get(position).services.get(0).item_name
                quantity="1"
                price=items.get(position).services.get(0).price
            }
            R.id.category_lyt->{
               // Toast.makeText(this,"click",Toast.LENGTH_SHORT).show()
                Log.d("items",itemList[position].items.toString())
                showItem(itemList[position].items)

            }


        }



}

    override fun onItemClick1(view: View, count: Int, position: Int) {
        when(view?.id) {

            R.id.service_minus_ib->{

                if (count>0){

                    // service_count_tv.text=count.toString()

                    // item_service_price_tv.text=(count*services.get(position).price.toInt()).toString()+"SAR"
                    if (count==1){
                        Log.d("servicesremove",servicelist.toString())
                        servicelist.remove(AddServiceModel(services.get(position)._id,services.get(position).service_type_name,services.get(position).item_id,services.get(position).item_name,count.toString(),(services.get(position).price.toInt()*count).toString()))
                    }
                    else{
                        for (item in servicelist){
                            if (item.item_id==servicelist[position].item_id){
                                item.price=(servicelist[position].price.toInt()*count).toString()
                                item.quantity=count.toString()
                            }
                        }
                    }
                }
            }
            R.id.service_plus_ib->{


                if (count==1){
                    servicelist.add(AddServiceModel(services.get(position)._id,services.get(position).service_type_name,services.get(position).item_id,services.get(position).item_name,count.toString(),(services.get(position).price.toInt()*count).toString()))
                    Log.d("servicesplus",servicelist.toString())
                }
                else{
                    for (item in servicelist){
                        if (item.item_id==servicelist[position].item_id){
                            item.price=(servicelist[position].price.toInt()*count).toString()
                            item.quantity=count.toString()
                        }
                    }
                }

            }

        }
    }

    private fun OpenBottomDialog(
        services: ArrayList<HomeDetailDataResponce.Response.Items.Services>,
        itemName: String
    ) {
        val dialog = BottomSheetDialog(this@LaundryDetailActivity)
        dialog.setContentView(R.layout.add_item_dialog_box)
        dialog.close_tv.setOnClickListener {
            dialog.dismiss()

        }
        dialog.item_name_tv.text=itemName
        Log.d("services",services.toString())
        serviceListAdapter = ServiceListAdapter(this, services,servicelist,this)

        dialog.service_list_recyclerview.adapter = serviceListAdapter
          dialog.add_item_in_cart_tv.setOnClickListener {
              proceed_to_payment.visibility=View.VISIBLE
              dialog.dismiss()
          }
        dialog.show()
    }

    override fun onItemLongClick(view: View?, position: Int) {

    }


}