package com.laundry.user.webservice




import com.laundry.user.Constants
import com.laundry.user.bean.*
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody

import retrofit2.http.*


interface
ApiInterface {

    //signup
    @FormUrlEncoded
    @POST(Constants.SIGNUP)
    fun signUp(
        @Field("country") country: String,
        @Field("mobileNumber") mobileNumber: String,
        @Field("email") email: String,
        @Field("city") city: String,
        @Field("deviceType") deviceType: String,
        @Field("deviceToken") deviceToken: String?,
        @Field("latitude") latitude: String?,
        @Field("longitude") longitude: String?,
        @Field("deviceId") deviceId: String?,
        @Field("password") password: String,
        @Field("countryCode") countryCode: String


    ): Observable<LoginResponse>

    //verify Otp

    @FormUrlEncoded
    @POST(Constants.VERIFY_OTP)
    fun verifyOtp(
        @Header("access_token") access_token: String,
        @Field("verification_code") verification_code: String

    ): Observable<LoginResponse>

     //Login

    @FormUrlEncoded
    @POST(Constants.LOGIN)
    fun login(

        @Field("mobileNumber") mobileNumber: String,
        @Field("deviceType")deviceType: String,
        @Field("deviceToken") deviceToken: String,
        @Field("latitude") latitude: String?,
        @Field("longitude") longitude: String?,
        @Field("password") password: String?,
        @Field("countryCode") countryCode: String?

    ): Observable<LoginResponse>

    //forget password
    @FormUrlEncoded
    @POST(Constants.FORGET_PASSWORD)
    fun forgetPassword(
        @Field("mobileNumber") mobileNumber: String,
        @Field("countryCode") countryCode: String
    ): Observable<LoginResponse>

    //reset Password

    @FormUrlEncoded
    @POST(Constants.RESET_PASSWORD)
    fun resetPassword(
        @Header("access_token") access_token: String,
        @Field("password") password: String

    ): Observable<LoginResponse>

    //Profile creation

    @Multipart
    @POST(Constants.PROFILE_CREATION)
    fun profile_creation(
        @Header("access_token") access_token: String,
        @Part("mobileNumber") mobileNumber: RequestBody,

        @Part profile_image: MultipartBody.Part? = null,
        @Part("name") name:RequestBody,
        @Part("house_number") house_number: RequestBody,
        @Part("building_details") building_details: RequestBody,
        @Part("Locality") Locality: RequestBody
    ): Observable<ProfileResponse>

    //edit profile
    @Multipart
    @POST(Constants.EDIT_PROFILE)
    fun updateUserProfile(
        @Header("access_token") access_token: String?,
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("mobileNumber") mobileNumber: RequestBody,
        @Part profile_image: MultipartBody.Part? = null
    ): Observable<ProfileResponse>


   // Laundry list
    @POST(Constants.GET_HOME)
    fun getHomeData(
        @Header("access_token") access_token: String?
    ): Observable<HomeDataResponse>

  //laundry detail

    @FormUrlEncoded
    @POST(Constants.GET_SHOP_DETAIL)
    fun getHomeDetailData(
        @Header("access_token") access_token:  String?,
        @Field("seller_id") seller_id: String
    ): Observable<HomeDetailDataResponce>

    //get time slot

    @FormUrlEncoded
    @POST(Constants.GET_TIME_SLOT)
    fun getTimeslot(
        @Header("access_token") access_token:  String?,
        @Field("seller_id") seller_id: String,
        @Field("slot_type") slot_type: String,
        @Field("day") day: String

    ): Observable<TimeSlotResponce>

    //add address

    @FormUrlEncoded
    @POST(Constants.ADD_ADDRESS)
    fun addAddress(
        @Header("access_token") access_token:  String?,
        @Field("address") address: String,
        @Field("house_no") house_no: String,
        @Field("landmark") landmark: String,
        @Field("address_type") address_type: String,
        @Field("latitude") latitude: String,
        @Field("longitude") longitude: String

    ): Observable<AddAddressResponce>

    //get address

    @GET(Constants.GET_ADDRESS)
    fun getAddress(
        @Header("access_token") access_token: String?
    ): Observable<GetAddressResponce>

    //place order

    @FormUrlEncoded
    @POST(Constants.PLACE_ORDER)
    fun placeOrder(
        @Header("access_token") access_token:  String?,
        @Field("cart_id") cart_id: String,
        @Field("address_id") address_id: String,
        @Field("note") note: String,
        @Field("payment_mode") payment_mode: String

    ): Observable<AddAddressResponce>


    @FormUrlEncoded
    @POST(Constants.ADD_ORDER)
    fun getAddOrder(
        @Field("seller_id") seller_id: String,
        @Field("pickup_time_slots") pickup_time_slots: String,
        @Field("delivery_time_slots") delivery_time_slots: String,
        @Field("service_type") service_type:String ,
        @Field("delivery_type") delivery_type: String,
        @Field("pickup_day") pickup_day: String,
        @Field("pickup_date") pickup_date: String,
        @Field("delivery_day") delivery_day: String,
        @Field("delivery_date") delivery_date: String
    ): Observable<AddOrderResponce>

    @FormUrlEncoded
    @POST(Constants.UPDATE_ORDER)
    fun getUpdateOrder(
        @Header("cart_id") cart_id: String,
        @Field("service_type") service_type: String,
        @Field("pickup_time_slots") pickup_time_slots: String,
        @Field("delivery_time_slots") delivery_time_slots:String,
        @Field("pickup_day") pickup_day: String,

        @Field("pickup_date") pickup_date: String,
        @Field("delivery_day") delivery_day: String,
        @Field("delivery_date") delivery_date: String
    ): Observable<AddOrderResponce>
}
