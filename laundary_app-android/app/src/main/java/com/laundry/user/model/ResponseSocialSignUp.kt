package com.laundry.user.model.response

data class ResponseSocialSignUp(
    var message: String?,
    var response: Response?
) {
    data class Response(

        var First_Name: String?,
        var Last_Name: String?,
        var gl_social_id:String?,
        var social_type:String?,
        var device_type: String?,
        var device_token: String?,
        var latitude: Double?,
        var longitude: Double?,
        var social_id: String?,
        var location: Location,
        var email: String?,
        var is_verified: String?,
        var is_social_active: String?) {
        data class Location(
            var coordinates: List<Double?>?,
            var type: String?
        )
    }
}