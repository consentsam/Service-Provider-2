package com.laundry.user.constants

class AppConstants {
    companion object{
        var SPLASH_TIME:Long = 4000
        var APP_EXIT_MESSAGE_TIME:Long = 1000

        const val EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"



        const val GALLERY = 1111
        const val DEVICE_TYPE = "1"
        const val COUNTRY_CODE = "+91"
        const val USER_TYPE = "1"
        const val CAMERA = 2222
        const val VIDEO = 3333
        const val MEDIA_TYPE_IMAGE = 1
        const val MEDIA_TYPE_VIDEO = 2
        const val PERMISSION_REQUEST_CODE = 1001
        const val CAMERA_PERMISSION_MESSAGE ="Allow JustWarranty to access photos, media, and files on your device?"
        const val LOCATION_PERMISSION_MESSAGE ="Allow JustWarranty to access this device's location?"
        const val STORAGE_PERMISSION_MESSAGE ="Allow JustWarranty to access this device's storage ?"
        const val USERS = "USERS"
        const val USER = "User"
        const val UNDERSCORE = "_"
        const val MESSAGE = "MESSAGE"
        const val UPDATE_LISTENER_TYPE = "UPDATE_LISTENER_TYPE"
        const val ADD_LISTENER_TYPE = "ADD_LISTENER_TYPE"
        const val ADD_VALUE_EVENT_LISTENER_TYPE = "ADD_VALUE_EVENT_LISTENER_TYPE"
        const val OFFLINE_QUERY_DATA = "OFFLINE_QUERY_DATA"


    }
}