package com.example.mobileapp.interactor

import com.example.mobileapp.data.network.response.error.CreateProfileErrorResponse
import com.example.mobileapp.data.network.response.error.SignInWithEmailErrorResponse
import com.example.mobileapp.domain.AuthTokens
import com.example.mobileapp.domain.User
import com.example.mobileapp.repository.AuthRepository
import com.haroldadmin.cnradapter.NetworkResponse
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject

class AuthInteractor @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend fun isAuthorizedFlow(): Flow<Boolean> =
        authRepository.isAuthorizedFlow()

    suspend fun signInWithEmail(email: String, password: String): NetworkResponse<AuthTokens, SignInWithEmailErrorResponse> {
        val response = authRepository.generateAuthTokensByEmail(email, password)
        when (response) {
            is NetworkResponse.Success -> {
                authRepository.saveAuthTokens(response.body)
            }
            is NetworkResponse.Error -> {
                Timber.e(response.error)
            }
        }
        return response
    }

    suspend fun signUpWithPersonalInfo(
        firstName: String,
        lastName: String,
        username: String,
        email: String,
        password: String
    ): NetworkResponse<User, CreateProfileErrorResponse> {
        val response = authRepository.generateUserByEmailAndPersonalInfo(firstName, lastName,
            username, email, password)
        when (response) {
            is NetworkResponse.Error -> {
                Timber.e(response.error)
            }
        }
        return response
    }

    suspend fun logout() {
        authRepository.saveAuthTokens(null)
    }
}