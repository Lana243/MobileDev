package com.example.mobileapp.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "id") val id: Long,
    @Json(name = "user_name") val username: String,
    @Json(name = "avatar") val avatarUrl: String?, // E.g. "https://mydomain.com/user_1_avatar.jpg"
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    @Json(name = "group_name") val groupName: String?
)
