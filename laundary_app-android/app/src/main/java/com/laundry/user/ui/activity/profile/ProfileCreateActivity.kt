package com.laundry.user.ui.activity.profile

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide

import com.laundry.user.R

import com.laundry.user.base.BaseActivity
import com.laundry.user.bean.ProfileResponse
import com.laundry.user.shared_prefernence.SharedPreferenceWriter
import com.laundry.user.ui.activity.DashboardActivity
import com.laundry.user.utils.getString
import com.laundry.user.utils.isValueEmpty
import com.laundry.user.utils.showToast
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import kotlinx.android.synthetic.main.activity_profile_create.*

import kotlinx.android.synthetic.main.button_layout.view.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody

import okhttp3.RequestBody
import java.io.File

class ProfileCreateActivity : BaseActivity(), View.OnClickListener {

    private var disposable: Disposable? = null
    private var access_token: String? = null
    private var mobile_number: String? = null
    private var email: String? = null
    private var imageType: Int = 0
    private var profileImage: String? = null
    private var isEdit = false
    private var isProfilePic = false
    private lateinit var sharedPreferenceWriter: SharedPreferenceWriter
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile_create)
        access_token = intent.extras?.getString("access_token")
        mobile_number = intent.extras?.getString("mobile_number")
        email = intent.extras?.getString("email")
        Log.e(" " + access_token, " " + access_token)
        sharedPreferenceWriter = SharedPreferenceWriter.getInstance(this)
        inItId()
        setListener()
    }

    override fun inItId() {
        inc_btn_profile_create.btn_submit.text = getString(R.string.profile_create)
        et_email_profile.setText(email)
        et_mobile_number_profile.setText(mobile_number)

    }

    override fun setListener() {
        img_profile.setOnClickListener(this)
        inc_btn_profile_create.btn_submit.setOnClickListener(this)


    }
    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_submit-> {
                // for empty check only
//                sendIntent()
                if (NetworkUtils.isInternetAvailable(this)) {
                    if (
                        et_name.isValueEmpty("name") &&

                        et_mobile_number_profile.isValueEmpty("mobile number") &&
                        et_email_profile.isValueEmpty("email id") &&
                        et_house_number.isValueEmpty("house number") &&
                        et_building_tower_name.isValueEmpty("building tower number")&&
                        et_street_locality_name.isValueEmpty("street locality name")

                    ) {
                        creteProfile()
                    }

                } else {
                    showToast(resources.getString(R.string.error_internet),applicationContext)
                }

            }
            R.id.img_profile->
            {
                val intent = CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .getIntent(this)
                startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
            }

        }
    }


    private fun creteProfile() {


        disposable = apiInterface.profile_creation(
            access_token = access_token.toString(),

            mobileNumber =getMultiPartyObjects(et_mobile_number_profile.getString().trim()),

            profile_image = prepareFilePart("profile_image", profileImage),
            name = getMultiPartyObjects(et_name.getString().trim()),
            house_number = getMultiPartyObjects(et_house_number.getString().trim()),
            building_details = getMultiPartyObjects(et_building_tower_name.getString().trim()),
            Locality=getMultiPartyObjects(et_street_locality_name.getString().trim())

        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {

                    onLoginSuccess(it)
                },
                {

                    onError(it)
                    showToast("Something went wrong",applicationContext)
                }
            )

    }

    private fun prepareFilePart(s: String, imagePath: String?): MultipartBody.Part? {

        if (imagePath == null)
            return null

        var selectedFile: File? = null
        imagePath.let {
            selectedFile = File(imagePath)
        }

        return selectedFile.let {
            MultipartBody.Part.createFormData(

                s,
                it!!.name,
                RequestBody.create("image/*".toMediaTypeOrNull(), it)
            )
        }

    }
    private fun getMultiPartyObjects(strVal: String): RequestBody {
        return RequestBody.create(
            "text/plain".toMediaTypeOrNull(), strVal
        )
    }

    private fun onLoginSuccess(response: ProfileResponse) {

        showToast(response.message,applicationContext)


        proceedFurther(response)

    }
    private fun proceedFurther(response: ProfileResponse) {
        val intent = Intent(this, DashboardActivity::class  .java)

        intent.putExtra("access_token",response.response!!.access_token)

        startActivity(intent)
        finish()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && data != null) {
            var result: CropImage.ActivityResult = CropImage.getActivityResult(data)
            if (resultCode == Activity.RESULT_OK) {
                img_profile.setImageURI(result.uri)
                profileImage= result.uri.toString()
                // handling and storing image in shared pref
                result.uri.path?.also {
                    val imgFile = File(it)
                 //  fileImg = imgFile
                    Glide.with(this).load(result.uri.path).placeholder(R.drawable.profle_crtn)
                        .circleCrop().into(img_profile)

                }
                if (result.uri != null) {
//                    sharedPreferenceUtil.profileImage = result.uri.toString ()
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error: Exception = result.error
            }
        }
    }
}