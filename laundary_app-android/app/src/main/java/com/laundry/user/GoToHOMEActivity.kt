package com.laundry.user

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.laundry.user.ui.activity.DashboardActivity
import com.laundry.user.ui.activity.SchedulePickupActivity
import kotlinx.android.synthetic.main.activity_go_to_h_o_m_e.*

class GoToHOMEActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_go_to_h_o_m_e)
        gotohome_btn.setOnClickListener {
            startActivity(Intent(this, DashboardActivity::class.java).apply {


                startActivity(intent)
                finish()
            }
            )
        }
    }
}