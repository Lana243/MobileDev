package com.example.mobileapp.data.network.response.error

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.example.mobileapp.domain.Error

@JsonClass(generateAdapter = true)
data class SendRegistrationVerificationCodeErrorResponse(
    @Json(name = "non_field_errors") val nonFieldErrors: List<Error>?
)