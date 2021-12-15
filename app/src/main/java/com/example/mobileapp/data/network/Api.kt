package com.example.mobileapp

import com.example.mobileapp.data.network.request.CreateProfileRequest
import com.example.mobileapp.data.network.request.RefreshAuthTokensRequest
import com.example.mobileapp.data.network.request.SignInWithEmailRequest
import com.example.mobileapp.data.network.response.error.*
import com.example.mobileapp.domain.AuthTokens
import com.example.mobileapp.domain.Post
import com.example.mobileapp.domain.User
import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.http.*

interface Api {
    @GET("users")
    suspend fun getUsers(): NetworkResponse<List<User>, Unit>

    @POST("auth/sign-in-with-email")
    suspend fun signInWithEmail(
        @Body request: SignInWithEmailRequest
    ): NetworkResponse<AuthTokens, SignInWithEmailErrorResponse>

    @POST("auth/refresh-tokens")
    suspend fun refreshAuthTokens(
        @Body request: RefreshAuthTokensRequest
    ): NetworkResponse<AuthTokens, RefreshAuthTokensErrorResponse>

    @POST("registration/verification-code/send")
    suspend fun sendRegistrationVerificationCode(
        @Query("email") email: String,
    ): NetworkResponse<Unit, SendRegistrationVerificationCodeErrorResponse>

    @POST("registration/verification-code/verify")
    suspend fun verifyRegistrationCode(
        @Query("code") code: String,
        @Query("email") email: String?,
        @Query("phone_number") phoneNumber: String?
    ): NetworkResponse<Unit, VerifyRegistrationCodeErrorResponse>

    @PUT("registration/create-profile")
    suspend fun createProfile(
        @Body request: CreateProfileRequest
    ): NetworkResponse<AuthTokens, CreateProfileErrorResponse>

    @POST("posts")
    suspend fun getPost(): NetworkResponse<List<Post>, Unit>

    @GET("users/get-profile")
    suspend fun getProfile(): NetworkResponse<User, Unit>
}