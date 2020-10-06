package com.laundry.user.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.laundry.user.GoToHOMEActivity
import com.laundry.user.R
import kotlinx.android.synthetic.main.activity_payment_confirmation.*

class PaymentConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment_confirmation)
        place_order_btn.setOnClickListener {
            if (rg_pyment_type.getCheckedRadioButtonId() == -1)
            {
              Toast.makeText(this,"Please Select Any one Payment Mode",Toast.LENGTH_SHORT).show()
            }
            else
            {
                startActivity(Intent(this, GoToHOMEActivity::class.java).apply {


                    startActivity(intent)
                    finish()
                })
            }

    }
}}