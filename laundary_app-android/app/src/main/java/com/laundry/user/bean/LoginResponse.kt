package com.laundry.user.bean

import com.google.gson.annotations.SerializedName

data class LoginResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("response")
    val response: Response
) {
    data class Response(
        @SerializedName("deviceType")
        val deviceType: String,
        @SerializedName("latitude")
        val latitude: String,
        @SerializedName("longitude")
        val longitude: String,
        @SerializedName("is_verified_id")
        val is_verifiedd: String,
        @SerializedName("profile_created")
        val profile_created: Int,
        @SerializedName("service_provide")
        val service_provide: String,
        @SerializedName("profile_image")
        val profile_image: String,
        @SerializedName("_id")
        val _id: String,
        @SerializedName("country")
        val country: String,
        @SerializedName("mobileNumber")
        val mobileNumber: String,
        @SerializedName("email")
        val email: String,


        @SerializedName("city")
        val city: String,
        @SerializedName("deviceToken")
        val deviceToken: String,
        @SerializedName("deviceId")
        val deviceId: String,
        @SerializedName("password")
        val password: String,
        @SerializedName("verification_code")
        val verification_code: String,
        @SerializedName("access_token")
        val access_token: String,
        @SerializedName("countryCode")
        val countryCode: String,
        @SerializedName("__v")
        val __v: String)

}