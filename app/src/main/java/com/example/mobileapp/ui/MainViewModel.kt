package com.example.mobileapp.ui

import com.example.mobileapp.interactor.AuthInteractor
import com.example.mobileapp.repository.AuthRepository
import com.example.mobileapp.repository.AuthRepositoryOld
import com.example.mobileapp.repository.AuthRepositoryOld.isAuthorizedFlow
import com.example.mobileapp.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val authInteractor: AuthInteractor
): BaseViewModel() {
    suspend fun isAuthorizedFlow(): Flow<Boolean> =
        authInteractor.isAuthorizedFlow()
}