package com.laundry.user.bean

import com.google.gson.annotations.SerializedName

data class ProfileResponse(
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
        val profile_created: String,
        @SerializedName("service_provide")
        val service_provide: String,
        @SerializedName("profile_image")
        val profile_image: String,
        @SerializedName("commercial_registation")
        val commercial_registation: String,
        @SerializedName("municipality_license")
        val municipality_license: String,
        @SerializedName("tax_registation_number")
        val tax_registation_number: String,
        @SerializedName("other_document")
        val other_document: String,
        @SerializedName("address_proof")
        val address_proof: String,
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
    val __v: String,
    @SerializedName("address")
    val address: String,
    @SerializedName("delivery_mobileNumer")
    val delivery_mobileNumer: String,
    @SerializedName("laudary_name")
    val laudary_name: String,
    @SerializedName("owner_name")
    val owner_name: String,
    @SerializedName("account_holder_name")
    val account_holder_name: String,
    @SerializedName("bank_account_number")
    val bank_account_number: String,
    @SerializedName("bank_name")
    val bank_name: String,
    @SerializedName("iban_code")
    val iban_code: String)

}