package com.laundry.user.bean

import com.google.gson.annotations.SerializedName

data class ShopDetailResponse(
    @SerializedName("message")
    val message: String,
    @SerializedName("response")
    val response:ArrayList<ShopDetailResponse.Response>
){
    data class Response(

        @SerializedName("category_name")
        val category_name: String,
        @SerializedName("items")
        val response:ArrayList<ShopDetailResponse.Response.items>
    ) {
        data class items(

            @SerializedName("item_name")
            val item_name: String,
            @SerializedName("items")
            val items: ArrayList<ShopDetailResponse.Response.items.services>
        )
        {
            data class services(

                @SerializedName("_id")
                val _id: String,
                @SerializedName("service_type")
                val service_type: String,
                @SerializedName("is_service_active")
                val is_service_active: String,

                @SerializedName("category")
                val category: Category,
                @SerializedName("item_id")
                val item_id: String,
                @SerializedName("price")
                val price: String,
                @SerializedName("service_type_name")
                val service_type_name: String,
                @SerializedName("item_name")
            val item_name: String,
            @SerializedName("seller_id")
            val seller_id: String,
                @SerializedName("__v")
                val __v: String


            )
            {
                data class Category(

                    @SerializedName("_id")
                    val _id: String,
                    @SerializedName("name")
                    val name: String,

                    @SerializedName("image")
                    val image: String,
                    @SerializedName("__v")
                    val __v: String


                )
            }
        }
    }
}