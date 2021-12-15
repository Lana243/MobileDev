package com.example.mobileapp.data.network.request

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class CreateProfileRequest(
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    @Json(name = "username") val username: String,
    @Json(name = "email") val email: String?,
    @Json(name = "password") val password: String
)