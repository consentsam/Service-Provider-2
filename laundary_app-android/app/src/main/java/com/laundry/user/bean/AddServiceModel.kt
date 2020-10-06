package com.laundry.user.bean

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class AddServiceModel(

    var service_type_id: String,
    var service_name:String,
    var item_id: String,
    var item_name:String,
    var quantity: String,
    var price:String
):Parcelable