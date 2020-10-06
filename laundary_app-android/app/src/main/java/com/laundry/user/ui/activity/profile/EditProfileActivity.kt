package com.laundry.user.ui.activity.profile

import android.app.Activity

import android.content.Intent
import android.os.Build

import android.os.Bundle
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide

import com.laundry.user.Constants
import com.laundry.user.R
import com.laundry.user.base.BaseActivity
import com.laundry.user.bean.ProfileResponse
import com.laundry.user.shared_prefernence.SharedPreferenceKey
import com.laundry.user.shared_prefernence.SharedPreferenceWriter
import com.laundry.user.utils.getString
import com.laundry.user.utils.showToast
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_edit_profile.*
import kotlinx.android.synthetic.main.activity_profile_create.*
import kotlinx.android.synthetic.main.button_layout.view.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

class EditProfileActivity: BaseActivity() {
    private var profileImage: String? = null
    private lateinit var sharedPreferenceWriter: SharedPreferenceWriter
    private var imageList: ArrayList<String> = ArrayList()
    private var disposable: Disposable? = null
    @RequiresApi(Build.VERSION_CODES.M)
    companion object {
        private const val IMAGE_PICK_CODE = 1000
    }


    /**
     * it all starts here
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)

        initialize()
        setClickListeners()
        getProfileData()
    }

    override fun inItId() {
        inc_btn_edit_profile_create.btn_submit.text = getString(R.string.update_profile)
    }

    override fun setListener() {

    }


    private fun initialize() {

        sharedPreferenceWriter = SharedPreferenceWriter.getInstance(this)


    }



    private fun getProfileData() {
        editProfile_name_et.setText(sharedPreferenceWriter.getString(SharedPreferenceKey.FIRST_NAME))
        et_mobile_number_edit_profile.setText(sharedPreferenceWriter.getString(SharedPreferenceKey.MOBILE_NUMBER))

        et_email_edit_profile.setText(sharedPreferenceWriter.getString(SharedPreferenceKey.EMAIL))




        if (sharedPreferenceWriter.getStringArray(SharedPreferenceKey.PROFILE_IMAGES) != null &&
            sharedPreferenceWriter.getStringArray(SharedPreferenceKey.PROFILE_IMAGES)!!.size > 0
        )
            Picasso.get()
                .load(
                    Constants.BASE_URL.plus(
                        sharedPreferenceWriter.getStringArray(SharedPreferenceKey.PROFILE_IMAGES)
                            ?.get(
                                0
                            )
                    )
                )
                .error(R.drawable.profle_crtn)
                .into(img_profile)
    }

    /**
     * all clicks handled here
     */
    private fun setClickListeners() {

        //Camera Image
        ivProfileImage.setOnClickListener {
            val intent = CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)
                .getIntent(this)
            startActivityForResult(intent, CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE)
        }




        //Save
        inc_btn_edit_profile_create.btn_submit.setOnClickListener {
            updateProfileData()
        }

    }


    /**
     * post the profile data to Server
     */
    private fun updateProfileData() {


        disposable = apiInterface.updateUserProfile(
            access_token = sharedPreferenceWriter.getString(SharedPreferenceKey.ACCESS_TOKEN),
            name = getMultiPartyObjects(editProfile_name_et.getString().trim()),
            email = getMultiPartyObjects(et_email_edit_profile.getString().trim()),
            mobileNumber = getMultiPartyObjects(et_mobile_number_edit_profile.getString().trim()),
            profile_image = prepareFilePart("profile_image", profileImage)
        ).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                {

                    onCheckSuccess(it)
                },
                {

                    onError(it)
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
    private fun onCheckSuccess(response: ProfileResponse?) {

        showToast(response!!.message!!, applicationContext)

        sharedPreferenceWriter.writeStringValue(
            SharedPreferenceKey.FIRST_NAME,
            editProfile_name_et.getString().trim()
        )
        sharedPreferenceWriter.writeStringValue(
            SharedPreferenceKey.EMAIL,
            et_email_edit_profile.getString().trim()
        )
        sharedPreferenceWriter.writeStringValue(
            SharedPreferenceKey.MOBILE_NUMBER,
            et_mobile_number_edit_profile.getString().trim()
        )
        sharedPreferenceWriter.writeStringArrayValue(
            SharedPreferenceKey.PROFILE_IMAGES,
            response.response!!.profile_image
        )
        finish()
    }


    private fun getMultiPartyObjects(strVal: String): RequestBody {
        return RequestBody.create(
            "text/plain".toMediaTypeOrNull(), strVal
        )
    }


    /**
     * activity result for picked image
     */
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
//                    fileImg = imgFile
                    Glide.with(this).load(result.uri.path).placeholder(R.drawable.profle_crtn)
                        .circleCrop().into(img_profile)
                }
                if (result.uri != null) {
//                    sharedPreferenceUtil.profileImage = result.uri.toString()
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                val error: Exception = result.error
            }
        }
    }
}