package com.example.mobileapp.interactor

import com.example.mobileapp.domain.User
import com.example.mobileapp.repository.UsersRepository
import com.haroldadmin.cnradapter.NetworkResponse
import javax.inject.Inject

class UsersInteractor @Inject constructor(
    private val usersRepository: UsersRepository
) {

    suspend fun loadUsers() : NetworkResponse<List<User>, Unit> =
        usersRepository.getUsers()

}