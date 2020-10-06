package com.laundry.user.bean

import com.google.gson.annotations.SerializedName


data class AddOrderResponce(
    @SerializedName("message")
    val message: String,
    @SerializedName("response")
    val response: Response
) {
    data class Response(
        @SerializedName("_id")
        val _id: String,
        @SerializedName("seller_id")
        val seller_id: String,
        @SerializedName("pickup_time_slots")
        val pickup_time_slots: Pickup_time_slots,
        @SerializedName("delivery_time_slots")
        val delivery_time_slots: Delivery_time_slots,
        @SerializedName("service_type")
        val service_type:ArrayList<Service_type>,
        @SerializedName("delivery_type")
        val delivery_type: String,
        @SerializedName("pickup_day")
        val pickup_day: String,
        @SerializedName("pickup_date")
        val pickup_date: String,
        @SerializedName("delivery_day")
        val delivery_day: String,
        @SerializedName("delivery_date")
        val delivery_date: String,
        @SerializedName("delivery_charge")
        val delivery_charge: String,
        @SerializedName("grand_total")
        val grand_total: String,
        @SerializedName("user_id")
        val user_id: String,
        @SerializedName("__v")
        val __v: String
    )


    {
        data class Pickup_time_slots(
            @SerializedName("start_time")
            val start_time: String,
            @SerializedName("service_type_id")
            val service_type_id: String


        )
        data class Delivery_time_slots(
            @SerializedName("start_time")
            val start_time: String,
            @SerializedName("service_type_id")
            val service_type_id: String

        )
        data class Service_type(
            @SerializedName("_id")
            val _id: String,
            @SerializedName("service_type_id")
            val service_type_id: String,
            @SerializedName("service_name")
            val service_name: String,
            @SerializedName("items")
            val items: ArrayList<Items>

        )


        {
            data class Items(
                @SerializedName("_id")
                val _id: String,
                @SerializedName("item_id")
                val item_id: String,
                @SerializedName("item_name")
                val item_name: String,
                @SerializedName("quantity")
                val quantity: String,
                @SerializedName("price")
                    val price: String



            )

        }

    }
}