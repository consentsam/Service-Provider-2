package com.laundry.user.ui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.GoogleMap
import com.laundry.user.R
import com.laundry.user.base.BaseActivity
import com.laundry.user.bean.AddAddressResponce
import com.laundry.user.shared_prefernence.SharedPreferenceKey
import com.laundry.user.shared_prefernence.SharedPreferenceWriter
import com.laundry.user.ui.activity.forgotpassword.ForgotPasswordActivity
import com.laundry.user.ui.activity.register.RegisterActivity
import com.laundry.user.utils.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_add_address.*
import kotlinx.android.synthetic.main.activity_login.*

class AddAddressActivity : BaseActivity(), View.OnClickListener {
    private val PERMISSION_ID = 42
    private var latitude: Double? = null
    private var longitude: Double? = null
    private var address: String? = null
    private lateinit var map: GoogleMap
    private lateinit var sharedPreferenceWriter: SharedPreferenceWriter
    private var disposable: Disposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_address)
        getLocation()

    }

    override fun inItId() {
        sharedPreferenceWriter = SharedPreferenceWriter.getInstance(this)
    }

    override fun setListener() {
       btn_submit_addAdress.setOnClickListener(this)
        address_back_tv.setOnClickListener(this)
    }


    /**
     * check and request permission for location
     */
    private fun checkPermission(vararg perm: String): Boolean {
        val havePermissions = perm.toList().all {
            ContextCompat.checkSelfPermission(this, it) ==
                    PackageManager.PERMISSION_GRANTED
        }
        if (!havePermissions) {
            if (perm.toList().any {
                    ActivityCompat.shouldShowRequestPermissionRationale(this, it)
                }
            ) {
                val dialog = AlertDialog.Builder(this)
                    .setTitle("Permission")
                    .setMessage("Permission needed!")
                    .setPositiveButton("OK") { id, v ->
                        ActivityCompat.requestPermissions(
                            this, perm, PERMISSION_ID
                        )
                    }
                    .setNegativeButton("No", { id, v -> })
                    .create()
                dialog.show()
            } else {
                ActivityCompat.requestPermissions(this, perm, PERMISSION_ID)
            }
            return false
        }
        return true
    }

    /**
     * permission result for location
     */
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            PERMISSION_ID -> {
                getLocation()
            }
        }
    }


    private fun getLocation() {

        if (checkPermission(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            val gpsTracker = GPSTracker(this, this)
            val location = gpsTracker.getLocation(this)
            if (location != null) {
                latitude = location.latitude
                longitude = location.longitude

                address = LocationUtil().addressLine(this, latitude, longitude)
                Log.d("address",latitude.toString()+longitude.toString()+address.toString())
                location_et.setText(address)

            }
            gpsTracker.stopSelf()
        }
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_submit_addAdress -> {
                // for empty check only
//                sendIntent()

                if (NetworkUtils.isInternetAvailable(this)) {
                    if (isValidInputs()) {
                        addAddress()
                    }

                } else {
                    showToast(resources.getString(R.string.error_internet),applicationContext)
                }
            }

        }
    }

    private fun addAddress() {
        disposable = apiInterface.addAddress(
            access_token = sharedPreferenceWriter.getString(SharedPreferenceKey.ACCESS_TOKEN),
            address = location_et.text.toString()+" "+house_no_et.text.toString()+" "+landmark_et.text.toString(),
            house_no= house_no_et.text.toString(),
            landmark= landmark_et.text.toString(),
            address_type="home",
            latitude=latitude.toString(),
            longitude=longitude.toString()

        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {

                    onLoginSuccess(it)
                },
                {

                    onError(it)
                    showToast("Invalid credentials",applicationContext)
                }
            )
    }

    private fun onLoginSuccess(it: AddAddressResponce?) {
        startActivity(Intent(this, OrderConfirmationActivity::class.java).apply {

            /*  putExtra("location_et", location_et.text.toString())
              putExtra("house_no_et", house_no_et.text.toString())
              putExtra("landmark_et",landmark_et.text.toString())
              putExtra("from", "addAdress")*/
            startActivity(intent)
            finish()
        })
    }

    private fun isValidInputs(): Boolean {


        if (location_et.getString().isEmpty()) {
            location_et.error = resources.getString(R.string.error_location)
            location_et.requestFocus()
            return false
        }
        if (!house_no_et.getString().isEmpty()) {
            house_no_et.error = resources.getString(R.string.error_house_no)
            house_no_et.requestFocus(house_no_et.length())
            return false
        }
        if (!landmark_et.getString().isEmpty()) {
            landmark_et.error = resources.getString(R.string.error_landmark)
            landmark_et.requestFocus(landmark_et.length())
            return false
        }
        return true
    }
}