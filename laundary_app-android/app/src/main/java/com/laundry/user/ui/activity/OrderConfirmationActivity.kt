package com.laundry.user.ui.activity

import android.content.Intent
import android.os.Bundle
import com.laundry.user.R
import com.laundry.user.base.BaseActivity
import com.laundry.user.shared_prefernence.SharedPreferenceKey
import com.laundry.user.shared_prefernence.SharedPreferenceWriter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_order_confirmation.*

class OrderConfirmationActivity : BaseActivity() {
    var start_time: String? = ""
    var end_time: String ?= ""
    var day: String = ""
    var Pstart_time: String = ""
    var Pend_time: String = ""
    var name: String = ""
    var cartid :String =""
    var  countone:Int=0
    var  countsec:Int=0
    private lateinit var sharedPreferenceWriter: SharedPreferenceWriter
    private var disposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_confirmation)
        if(intent.getStringExtra("from").equals("sechedelivery")) {
            name = intent.getStringExtra("name")
            Pstart_time = intent.getStringExtra("Pstart_time")
            Pend_time = intent.getStringExtra("Pend_time")
            start_time = intent.getStringExtra("Estart_time")
            end_time = intent.getStringExtra("Eend_time")
            cartid = intent.getStringExtra("cartid")
            home_laundry_name_tv.text = name
            pickupTime_tv.text = Pstart_time + "-" + Pend_time
            DeliveryTime_tv.text = start_time + "-" + end_time
        }
        proceed_to_payment_btn.setOnClickListener {
            startActivity(Intent(this, PaymentConfirmationActivity::class.java).apply {


                startActivity(intent)
                finish()
            })
        }



        change_address_tv.setOnClickListener {
            startActivity(Intent(this, AddAddressActivity::class.java).apply {


                startActivity(intent)
                finish()
            })
        }

        minus_sec_tv.setOnClickListener {
            if (countsec > 0) {
                countsec = countsec - 1
                count_sec_tv.setText(countsec.toString())
            }
        }
        plus_sec_ib.setOnClickListener {

            countsec = countsec + 1
            count_sec_tv.setText(countsec.toString())
        }
            minus_one_ib.setOnClickListener {
                if (countone > 0) {
                    countone = countone - 1
                    count_one_tv.setText(countone.toString())
                }
            }
            plus_one_iv.setOnClickListener {

                countone = countone + 1
                count_one_tv.setText(countone.toString())

            }



    }
    override fun inItId() {
        sharedPreferenceWriter = SharedPreferenceWriter.getInstance(this)

    }

    override fun setListener() {

    }
}