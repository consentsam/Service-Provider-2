package com.laundry.user.bean

import com.google.gson.annotations.SerializedName

data class AddAddressResponce(
    @SerializedName("message")
    val message: String,
    @SerializedName("response")
    val response: Response
) {
    data class Response(
        @SerializedName("location")
        val location: Location,
        @SerializedName("latitude")
        val latitude: String,
        @SerializedName("longitude")
        val longitude: String,
        @SerializedName("_id")
        val _id: String,
        @SerializedName("address")
        val address: String,
        @SerializedName("house_no")
        val house_no: Int,
        @SerializedName("landmark")
        val landmark: String,
        @SerializedName("address_type")
        val address_type: String,
        @SerializedName("user_id")
        val user_id: String,
        @SerializedName("__v")
        val __v: String){
        data class Location(
            @SerializedName("type")
            val type: String,
            @SerializedName("coordinates")
            val coordinates: ArrayList<String>
        )
    }




}