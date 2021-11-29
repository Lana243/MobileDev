package com.example.mobileapp.repository

import com.example.mobileapp.Api
import com.example.mobileapp.domain.User
import com.haroldadmin.cnradapter.NetworkResponse
import javax.inject.Inject

class UsersRepository @Inject constructor(
    private val api: Api
) {

    suspend fun getUsers() : NetworkResponse<List<User>, Unit> =
        api.getUsers()

}