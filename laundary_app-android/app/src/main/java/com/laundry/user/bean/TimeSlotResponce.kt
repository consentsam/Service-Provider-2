package com.laundry.user.bean

import com.google.gson.annotations.SerializedName

data class TimeSlotResponce(
    @SerializedName("message")
    val message: String,
    @SerializedName("response")
    val response:ArrayList<TimeSlotResponce.Response>
) {
    data class Response(
        @SerializedName("_id")
        val _id: String,
        @SerializedName("time_slots")
        val time_slots:ArrayList<TimeSlotResponce.Response.Time_slots>,
        @SerializedName("day")
        val day: String,
        @SerializedName("slot_type")
        val slot_type: String,
        @SerializedName("seller_id")
        val seller_id: String,
        @SerializedName("__v")
        val __v: String
    ) {
        data class Time_slots(
            @SerializedName("_id")
            val _id: String,
            @SerializedName("end_time")
            val end_time: String,
            @SerializedName("start_time")
            val start_time: String
        )

    }

}