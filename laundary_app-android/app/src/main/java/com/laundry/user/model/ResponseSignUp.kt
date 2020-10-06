package com.laundry.user.model.response

data class ResponseSignUp(
    var message: String?,
    var response: Response?
) {
    data class Response(
        var location: Location,
        var country_code: String?,
        var is_verified: String?,
        var gender: String?,
        var dob: String?,
        // var profile_image: String?,
        var is_profile_created: String?,
        var is_social_active: String?,
        var social_id: String?,
        var _id: String?,
        var First_Name: String?,
        var Last_Name: String?,
        var mobile_number: String?,
        var email: String?,
        var password: String?,
        var device_type: String?,
        var device_token: String?,
        var access_token: String?,
        var created_on: String?,
        var modified_on: String?,
        var verification_code: String?,
        var latitude: Double?,
        var longitude: Double?
    ) {
        data class Location(
            var coordinates: List<Double?>?,
            var type: String?
        )
    }
}