package com.laundry.user.model.response

import com.google.gson.annotations.SerializedName

data class ResponseMessage(
    var message: String?,

    @SerializedName("cart_quantity")
    var cartQuantity: Int?
)