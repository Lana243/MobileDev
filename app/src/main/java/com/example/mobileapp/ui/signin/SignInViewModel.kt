package com.example.mobileapp.ui.signin

import androidx.lifecycle.viewModelScope
import com.example.mobileapp.repository.AuthRepository
import com.example.mobileapp.ui.base.BaseViewModel
import kotlinx.coroutines.launch

class SignInViewModel : BaseViewModel() {

    fun signIn() {
        viewModelScope.launch {
            AuthRepository.signIn()
        }
    }
}