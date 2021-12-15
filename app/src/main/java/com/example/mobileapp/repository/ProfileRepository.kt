package com.example.mobileapp.repository

import com.example.mobileapp.Api
import com.example.mobileapp.domain.User
import com.haroldadmin.cnradapter.NetworkResponse
import javax.inject.Inject

class ProfileRepository @Inject constructor(
    private val api: Api
) {

    suspend fun getProfile() : NetworkResponse<User, Unit> =
        api.getProfile()

}