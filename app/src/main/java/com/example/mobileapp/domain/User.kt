package com.example.mobileapp.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class User(
    @Json(name = "id") val id: Long,
    @Json(name = "user_name") val username: String,
    @Json(name = "picture") var avatarUrl: String?, //media.zenfs.com/en-US/homerun/time_72/ad41bb42a3e7be9eb1e32a9f2097b80f", // E.g. "https://mydomain.com/user_1_avatar.jpg"
    @Json(name = "first_name") val firstName: String,
    @Json(name = "last_name") val lastName: String,
    @Json(name = "group_name") val groupName: String?
)
