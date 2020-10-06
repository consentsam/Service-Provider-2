package com.laundry.user.ui.activity

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import com.laundry.user.R
import com.laundry.user.adapter.AllDataListAdapter
import com.laundry.user.adapter.PickUpTimeAdapter
import com.laundry.user.base.BaseActivity
import com.laundry.user.bean.AddOrderResponce
import com.laundry.user.bean.AddServiceModel
import com.laundry.user.bean.TimeslotModel
import com.laundry.user.listner.RecyclerItemClickListen
import com.laundry.user.shared_prefernence.SharedPreferenceKey
import com.laundry.user.shared_prefernence.SharedPreferenceWriter
import com.laundry.user.utils.showToast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_schedule_delivery.*
import kotlinx.android.synthetic.main.activity_schedule_pickup.*
import kotlinx.android.synthetic.main.activity_schedule_pickup.btn_addTimeSlot
import kotlinx.android.synthetic.main.activity_schedule_pickup.iv_slot_coll_back
import kotlinx.android.synthetic.main.activity_schedule_pickup.rb_eleven_twelve
import kotlinx.android.synthetic.main.activity_schedule_pickup.rb_nine_ten
import kotlinx.android.synthetic.main.activity_schedule_pickup.rb_ten_eleven
import kotlinx.android.synthetic.main.activity_schedule_pickup.rb_three_four
import kotlinx.android.synthetic.main.activity_schedule_pickup.rb_twelve_one
import kotlinx.android.synthetic.main.activity_schedule_pickup.rb_two_three
import kotlinx.android.synthetic.main.activity_view_all.*
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

class ScheduleDeliveryActivity : BaseActivity(), View.OnClickListener,
    CompoundButton.OnCheckedChangeListener {
    var start_time: String = ""
    var end_time: String = ""
    var day: String = ""
    var Pstart_time: String = ""
    var Pend_time: String = ""
    var name: String = ""
    var id: String=""
    var servicelist = ArrayList<AddServiceModel>()

    private var disposable: Disposable? = null
    private lateinit var sharedPreferenceWriter: SharedPreferenceWriter

    var access_token: String = ""
    private var pickTimedapter: PickUpTimeAdapter? = null
    val list = ArrayList<TimeslotModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_delivery)
        name = intent.getStringExtra("name")?:""
        Pstart_time = intent.getStringExtra("Pstart_time")?:""
        Pend_time     = intent.getStringExtra("Pend_time")?:""

        id = intent.getStringExtra("id")
        servicelist = intent.getParcelableArrayListExtra("servicelist")
        Log.d("serviceList",servicelist.toString())



        val cal: Calendar = Calendar.getInstance()
        // cal.setTime(yourDate)
        cal.add(Calendar.DATE, 0)
        val sdf = SimpleDateFormat("dd MMMM") //Date and time
        val sdf_ = SimpleDateFormat("EEEE")
        val cal1: Calendar = Calendar.getInstance()
        // cal.setTime(yourDate)
        cal1.add(Calendar.DATE, 1)


        val cal2: Calendar = Calendar.getInstance()
        // cal.setTime(yourDate)
        cal2.add(Calendar.DATE, 2)
        val cal3: Calendar = Calendar.getInstance()
        // cal.setTime(yourDate)
        cal3.add(Calendar.DATE, 3)
        val cal4: Calendar = Calendar.getInstance()
        // cal.setTime(yourDate)
        cal4.add(Calendar.DATE, 4)

        val cal5: Calendar = Calendar.getInstance()
        // cal.setTime(yourDate)
        cal5.add(Calendar.DATE, 5)
        val cal6: Calendar = Calendar.getInstance()
        // cal.setTime(yourDate)
        cal6.add(Calendar.DATE, 6)
        dtv_pick_month1.setText(sdf.format(cal.getTime()))
        dtv_pick_day1.setText(sdf_.format(cal.getTime()))
        dtv_pick_month2.setText(sdf.format(cal1.getTime()))
        dtv_pick_day2.setText(sdf_.format(cal1.getTime()))
        dtv_pick_month3.setText(sdf.format(cal2.getTime()))
        dtv_pick_day3.setText(sdf_.format(cal2.getTime()))
        dtv_pick_month4.setText(sdf.format(cal3.getTime()))
        dtv_pick_day4.setText(sdf_.format(cal3.getTime()))
        dtv_pick_month5.setText(sdf.format(cal4.getTime()))
        dtv_pick_day5.setText(sdf_.format(cal4.getTime()))
        dtv_pick_month6.setText(sdf.format(cal5.getTime()))
        dtv_pick_day6.setText(sdf_.format(cal5.getTime()))
        dtv_pick_month7.setText(sdf.format(cal6.getTime()))
        dtv_pick_day7.setText(sdf_.format(cal6.getTime()))
        inItId()
        setListener()

    }

    override fun inItId() {
        sharedPreferenceWriter = SharedPreferenceWriter.getInstance(this)
    }

    override fun setListener() {
        iv_slot_coll_back.setOnClickListener(this)
        btn_addTimeSlot.setOnClickListener(this)
        rb_nine_ten.setOnCheckedChangeListener(this)
        rb_ten_eleven.setOnCheckedChangeListener(this)
        rb_eleven_twelve.setOnCheckedChangeListener(this)
        rb_twelve_one.setOnCheckedChangeListener(this)
        rb_two_three.setOnCheckedChangeListener(this)
        rb_three_four.setOnCheckedChangeListener(this)
        dpick_up_card1.setOnClickListener(this)
        dpick_up_card2.setOnClickListener(this)
        dpick_up_card3.setOnClickListener(this)
        dpick_up_card4.setOnClickListener(this)
        dpick_up_card5.setOnClickListener(this)
        dpick_up_card6.setOnClickListener(this)
        dpick_up_card7.setOnClickListener(this)
    }


    /* private fun addDataSet() {

         val pictUpData = PickUpStaticData.createDataSet()
         //  val timeSlotData = SlotTimeStaticData.createDataSet()

         pickTimedapter.submitList(pictUpData)
 //        slotTimedapter.submitList(timeSlotData)

     }*/

    /* private fun pickUpRecyclerView() {
         rv_pick_time_list.apply {
            // layoutManager = GridLayoutManager(this@SlotCollectionActivity, 1)
        layoutManager = LinearLayoutManager(this@SlotCollectionActivity, LinearLayoutManager.HORIZONTAL ,false)

             pickTimedapter = PickUpTimeAdapter()
             adapter = pickTimedapter

         }
     }*/





    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.iv_slot_coll_back -> {

                onBackPressed()
            }


            R.id.dpick_up_card1 -> {
                dpick_up_card1.setCardBackgroundColor(Color.parseColor("#6200EE"))
                dpick_up_card2.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card3.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card4.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card5.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card6.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card7.setCardBackgroundColor(Color.parseColor("#144789"))
                if (dtv_pick_day1.text =="Sunday")
                {
                    day="1"
                }
                if (dtv_pick_day1.text =="Monday")
                {
                    day="2"
                }
                if (dtv_pick_day1.text =="Tuesday")
                {
                    day="3"
                }
                if (dtv_pick_day1.text =="Wednesday")
                {
                    day="4"
                }
                if (dtv_pick_day1.text =="Thursday")
                {
                    day="5"
                }
                if (dtv_pick_day1.text =="Friday")
                {
                    day="6"
                }
                if (dtv_pick_day1.text =="Saturday")
                {
                    day="7"
                }


            }
            R.id.dpick_up_card2 -> {
                dpick_up_card2.setCardBackgroundColor(Color.parseColor("#6200EE"))
                dpick_up_card1.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card3.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card4.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card5.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card6.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card7.setCardBackgroundColor(Color.parseColor("#144789"))
                if (dtv_pick_day2.text =="Sunday")
                {
                    day="1"
                }
                if (dtv_pick_day2.text =="Monday")
                {
                    day="2"
                }
                if (dtv_pick_day2.text =="Tuesday")
                {
                    day="3"
                }
                if (dtv_pick_day2.text =="Wednesday")
                {
                    day="4"
                }
                if (dtv_pick_day2.text =="Thursday")
                {
                    day="5"
                }
                if (dtv_pick_day2.text =="Friday")
                {
                    day="6"
                }
                if (dtv_pick_day2.text =="Saturday")
                {
                    day="7"
                }

            }
            R.id.dpick_up_card3 -> {
                dpick_up_card3.setCardBackgroundColor(Color.parseColor("#6200EE"))
                dpick_up_card2.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card1.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card4.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card5.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card6.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card7.setCardBackgroundColor(Color.parseColor("#144789"))
                if (dtv_pick_day3.text =="Sunday")
                {
                    day="1"
                }
                if (dtv_pick_day3.text =="Monday")
                {
                    day="2"
                }
                if (dtv_pick_day3.text =="Tuesday")
                {
                    day="3"
                }
                if (dtv_pick_day3.text =="Wednesday")
                {
                    day="4"
                }
                if (dtv_pick_day3.text =="Thursday")
                {
                    day="5"
                }
                if (dtv_pick_day3.text =="Friday")
                {
                    day="6"
                }
                if (dtv_pick_day3.text =="Saturday")
                {
                    day="7"
                }

            }
            R.id.dpick_up_card4 -> {
                dpick_up_card4.setCardBackgroundColor(Color.parseColor("#6200EE"))
                dpick_up_card2.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card3.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card1.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card5.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card6.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card7.setCardBackgroundColor(Color.parseColor("#144789"))
                if (dtv_pick_day4.text =="Sunday")
                {
                    day="1"
                }
                if (dtv_pick_day4.text =="Monday")
                {
                    day="2"
                }
                if (dtv_pick_day4.text =="Tuesday")
                {
                    day="3"
                }
                if (dtv_pick_day4.text =="Wednesday")
                {
                    day="4"
                }
                if (dtv_pick_day4.text =="Thursday")
                {
                    day="5"
                }
                if (dtv_pick_day4.text =="Friday")
                {
                    day="6"
                }
                if (dtv_pick_day4.text =="Saturday")
                {
                    day="7"
                }

            }
            R.id.dpick_up_card5 -> {
                dpick_up_card5.setCardBackgroundColor(Color.parseColor("#6200EE"))
                dpick_up_card2.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card3.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card4.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card1.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card6.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card7.setCardBackgroundColor(Color.parseColor("#144789"))
                if (dtv_pick_day5.text =="Sunday")
                {
                    day="1"
                }
                if (dtv_pick_day5.text =="Monday")
                {
                    day="2"
                }
                if (dtv_pick_day5.text =="Tuesday")
                {
                    day="3"
                }
                if (dtv_pick_day5.text =="Wednesday")
                {
                    day="4"
                }
                if (dtv_pick_day5.text =="Thursday")
                {
                    day="5"
                }
                if (dtv_pick_day5.text =="Friday")
                {
                    day="6"
                }
                if (dtv_pick_day5.text =="Saturday")
                {
                    day="7"
                }

            }
            R.id.btn_addTimeSlot -> {
                // Log.d("tag",list.size.toString())

                if (NetworkUtils.isInternetAvailable(this)) {
                    if (isValidInputs()) {
                     /*   startActivity(Intent(this, OrderConfirmationActivity::class.java).apply {
                            putExtra("name", name)
                            putExtra("Pstart_time", Pstart_time)
                            putExtra("Pend_time", Pend_time)
                            putExtra("Dstart_time", start_time)
                            putExtra("Dend_time", end_time)
                            putExtra("from", "sechedelivery")
                            // 0 = user & 1= provider

                            startActivity(intent)
                            finish()
                        })*/
                        // access_token= SharePrefUtils.getSharedInstance(this).accessToken!!
                        val jsonarray= JSONArray()

                            val json1 = JSONObject()
                       /*    json1.put("service_type_id",service_type_id)
                          json1.put("service_name",service_name)
                        json1.put("item_id",item_id)
                        json1.put("item_name",item_name)
                        json1.put("quantity",quantity)
                        json1.put("price",price)*/
                            jsonarray.put(json1)
                        val json = JSONObject()
                        json.put("start_time",start_time)
                        json.put("end_time",end_time)

                        val json2 = JSONObject()
                        json2.put("start_time",Pstart_time)
                        json2.put("end_time",Pend_time)
                        Log.d("logcart",json.toString()+json2.toString()+jsonarray.toString())
                        disposable = apiInterface.getAddOrder(

                            seller_id=id,
                            pickup_time_slots=json2.toString(),
                            delivery_time_slots=json.toString(),
                            service_type=jsonarray.toString(),
                            delivery_type="1",
                            pickup_day="2",
                            pickup_date="1599731861670",
                            delivery_day="4",
                            delivery_date="1599731861670"



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
                        // senTimeSlot(access_token)
                    }

                }
            }
            R.id.dpick_up_card6 -> {
                dpick_up_card6.setCardBackgroundColor(Color.parseColor("#6200EE"))
                dpick_up_card2.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card3.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card4.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card5.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card1.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card7.setCardBackgroundColor(Color.parseColor("#144789"))
                if (dtv_pick_day6.text =="Sunday")
                {
                    day="1"
                }
                if (dtv_pick_day6.text =="Monday")
                {
                    day="2"
                }
                if (dtv_pick_day6.text =="Tuesday")
                {
                    day="3"
                }
                if (dtv_pick_day6.text =="Wednesday")
                {
                    day="4"
                }
                if (dtv_pick_day6.text =="Thursday")
                {
                    day="5"
                }
                if (dtv_pick_day6.text =="Friday")
                {
                    day="6"
                }
                if (dtv_pick_day6.text =="Saturday")
                {
                    day="7"
                }

            }
            R.id.dpick_up_card7 -> {
                dpick_up_card7.setCardBackgroundColor(Color.parseColor("#6200EE"))
                dpick_up_card2.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card3.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card4.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card5.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card6.setCardBackgroundColor(Color.parseColor("#144789"))
                dpick_up_card1.setCardBackgroundColor(Color.parseColor("#144789"))
                if (dtv_pick_day7.text =="Sunday")
                {
                    day="1"
                }
                if (dtv_pick_day7.text =="Monday")
                {
                    day="2"
                }
                if (dtv_pick_day7.text =="Tuesday")
                {
                    day="3"
                }
                if (dtv_pick_day7.text =="Wednesday")
                {
                    day="4"
                }
                if (dtv_pick_day7.text =="Thursday")
                {
                    day="5"
                }
                if (dtv_pick_day7.text =="Friday")
                {
                    day="6"
                }
                if (dtv_pick_day7.text =="Saturday")
                {
                    day="7"
                }

            }

        }
    }

    private fun onSuccessResponse(it: AddOrderResponce?) {

        startActivity(Intent(this, OrderConfirmationActivity::class.java).apply {
            putExtra("name", name)
            putExtra("Pstart_time", Pstart_time)
            putExtra("Pend_time", Pend_time)
            putExtra("Dstart_time", start_time)
            putExtra("Dend_time", end_time)
            putExtra("from", "sechedelivery")
            putExtra("cartid", it!!.response._id)
            // 0 = user & 1= provider

            startActivity(intent)
            finish()
        })
    }


    private fun isValidInputs(): Boolean {
        if (list.size<1){
            showToast("Please Check atleast One Time Slot",applicationContext)
            return false
        }
        if (day.isEmpty()) {
            showToast("Please Select Date",applicationContext)
            return false
        }
        return true

    }



    override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
        when (buttonView?.id) {

            R.id.rb_nine_ten -> {
                if (isChecked==true) {
                    start_time = "9:00"
                    end_time = "10:00"
                    list.add(TimeslotModel(start_time, end_time))
                }
                if (isChecked==false){
                    start_time = "9:00"
                    end_time = "10:00"
                    list.remove(TimeslotModel(start_time, end_time))
                }
            }
            R.id.rb_ten_eleven -> {
                if (isChecked==true) {
                    start_time = "10:00"
                    end_time = "11:00"
                    list.add(TimeslotModel(start_time, end_time))
                }
                if (isChecked==false){
                    start_time = "10:00"
                    end_time = "11:00"
                    list.remove(TimeslotModel(start_time, end_time))
                }
            }
            R.id.rb_eleven_twelve -> {
                if (isChecked==true) {
                    start_time = "11:00"
                    end_time = "12:00"
                    list.add(TimeslotModel(start_time, end_time))
                }
                if (isChecked==false){
                    start_time = "11:00"
                    end_time = "12:00"
                    list.remove(TimeslotModel(start_time, end_time))
                }
            }
            R.id.rb_twelve_one -> {
                if (isChecked==true) {
                    start_time = "12:00"
                    end_time = "13:00"
                    list.add(TimeslotModel(start_time, end_time))
                }
                if (isChecked==false){
                    start_time = "12:00"
                    end_time = "13:00"
                    list.remove(TimeslotModel(start_time, end_time))
                }

            }
            R.id.rb_two_three -> {
                if (isChecked==true) {
                    start_time = "14:00"
                    end_time = "15:00"
                    list.add(TimeslotModel(start_time, end_time))
                }
                if (isChecked==false){
                    start_time = "14:00"
                    end_time = "15:00"
                    list.remove(TimeslotModel(start_time, end_time))
                }
            }
            R.id.rb_three_four -> {
                if (isChecked==true) {
                    start_time = "15:00"
                    end_time = "16:00"
                    list.add(TimeslotModel(start_time, end_time))
                }
                if (isChecked==false){
                    start_time = "15:00"
                    end_time = "16:00"
                    list.remove(TimeslotModel(start_time, end_time))
                }

            }
        }
    }

}
