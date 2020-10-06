package com.laundry.user

interface Constants {

    companion object {

        //Patterns
        const val EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)\$"
        const val PASSWORD_PATTERN = "^(?=.*\\d)(?=.*[A-Z]).{8,255}\$"


        //Api Constants
        const val BASE_URL = "http://15.207.1.62:3536/"

        const val DEVICE_TYPE_ANDROID = 1
        const val PIKFILE_RESULT_CODE2 = 2
        const val PICKFILE_RESULT_CODE3 = 3
        const val PICKFILE_RESULT_CODE4 = 4
        const val PICKFILCKFILE_RESULT_CODE1 = 1
        const val PICE_RESULT_CODE5 = 5 
        //User
        const val SIGNUP = "/user/signup"
        const val VERIFY_OTP = "user/verifyOtp"
        const val RESEND_OTP = "users/resend_otp"
        const val FORGET_PASSWORD = "user/forgetPassword"
        const val RESET_PASSWORD = "user/resetPassword"
        const val LOGIN = "user/login"
        const val PROFILE_CREATION = "user/createProfile"
        const val EDIT_PROFILE = "user/editProfile"
        const val GET_HOME = "user/home"
        const val GET_SHOP_DETAIL = "user/getshopDetail"
        const val GET_TIME_SLOT="user/get_time_slot"
        const val ADD_ADDRESS="user/add_address"
        const val GET_ADDRESS="user/get_address"
        const val PLACE_ORDER="user/place_order"
        const val ADD_ORDER="user/add_to_cart"
        const val UPDATE_ORDER="user/update_cart"
    }


}
