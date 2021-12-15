package com.example.mobileapp.data.network

import com.example.mobileapp.Api
import com.example.mobileapp.data.network.request.CreateProfileRequest
import com.example.mobileapp.data.network.request.RefreshAuthTokensRequest
import com.example.mobileapp.data.network.request.SignInWithEmailRequest
import com.example.mobileapp.data.network.response.error.*
import com.example.mobileapp.domain.AuthTokens
import com.example.mobileapp.domain.Post
import com.example.mobileapp.domain.User
import com.haroldadmin.cnradapter.NetworkResponse
import timber.log.Timber
import java.lang.Exception
import kotlin.random.Random

class MockApi : Api {

    private val userList = listOf(
        User(
            id = 7,
            firstName = "Michael",
            lastName = "Lawson",
            avatarUrl = "https://reqres.in/img/faces/7-image.jpg",
            username = "Michael",
            groupName = "Б09.мкн"
        ),
        User(
            id = 4,
            firstName = "George",
            lastName = "VI",
            username = "Gosha",
            avatarUrl = null,
            groupName = null
        ),
        User(
            id = 8,
            firstName = "Masha",
            lastName = "Nazarova",
            username = "Mary",
            avatarUrl = null,
            groupName = null
        ),
        User(
            id = 9,
            firstName = "Igor",
            lastName = "Petrov",
            username = "igor95",
            avatarUrl = null,
            groupName = null
        ),
        User(
            id = 10,
            firstName = "Masha",
            lastName = "Nazarova",
            username = "Mary2",
            avatarUrl = null,
            groupName = null
        ),
        User(
            id = 11,
            firstName = "Masha",
            lastName = "Nazarova",
            username = "Mary3",
            avatarUrl = null,
            groupName = null
        )
    )

    private val randomizer = Random(1337)

    override suspend fun getUsers(): NetworkResponse<List<User>, Unit> {
        val success = randomizer.nextBoolean()
        Timber.d("Try to load users. Success: %s", success)
        if (success)
            return NetworkResponse.Success(
                body = userList,
                code = 200
            )
        if (randomizer.nextBoolean())
            return NetworkResponse.Success(body= emptyList(), code=200)

        return NetworkResponse.UnknownError(Exception("Упс..."), code=404)
    }

    override suspend fun signInWithEmail(request: SignInWithEmailRequest): NetworkResponse<AuthTokens, SignInWithEmailErrorResponse> {
        return NetworkResponse.Success(
            AuthTokens(
                accessToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dnZWRJbkFzIjoiYWRtaW4iLCJpYXQiOjE0MjI3Nzk2MzgsImV4cCI6MTY0MDg3MTc3MX0.gzSraSYS8EXBxLN_oWnFSRgCzcmJmMjLiuyu5CSpyHI",
                refreshToken = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJsb2dnZWRJbkFzIjoiYWRtaW4iLCJpYXQiOjE0MjI3Nzk2MzgsImV4cCI6MTY0MDg3MTc3MX0.gzSraSYS8EXBxLN_oWnFSRgCzcmJmMjLiuyu5CSpyHI",
                accessTokenExpiration = 1640871771000,
                refreshTokenExpiration = 1640871771000,
            ),
            code = 200
        )
    }

    override suspend fun refreshAuthTokens(request: RefreshAuthTokensRequest): NetworkResponse<AuthTokens, RefreshAuthTokensErrorResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun sendRegistrationVerificationCode(email: String): NetworkResponse<Unit, SendRegistrationVerificationCodeErrorResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun verifyRegistrationCode(
        code: String,
        email: String?,
        phoneNumber: String?
    ): NetworkResponse<Unit, VerifyRegistrationCodeErrorResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun createProfile(request: CreateProfileRequest): NetworkResponse<User, CreateProfileErrorResponse> {
        TODO("Not yet implemented")
    }

    override suspend fun getPost(): NetworkResponse<List<Post>, Unit> {
        TODO("Not yet implemented")
    }

    override suspend fun getProfile(): NetworkResponse<User, Unit> {
        return NetworkResponse.Success(
            userList[0],
            code = 200
        )
    }
}