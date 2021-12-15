package com.example.mobileapp.ui.signup

import androidx.lifecycle.viewModelScope
import com.example.mobileapp.data.network.response.error.CreateProfileErrorResponse
import com.example.mobileapp.data.network.response.error.SignInWithEmailErrorResponse
import com.example.mobileapp.interactor.AuthInteractor
import com.example.mobileapp.repository.AuthRepositoryOld
import com.example.mobileapp.ui.base.BaseViewModel
import com.example.mobileapp.ui.signin.SignInViewModel
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authInteractor: AuthInteractor
) : BaseViewModel() {

    private val _signUpActionStateFlow = MutableStateFlow<SignUpActionState>(SignUpActionState.Pending)

    fun signUpActionStateFlow(): Flow<SignUpActionState> {
        return _signUpActionStateFlow.asStateFlow()
    }

    private suspend fun signIn(email: String, password: String) {
        _signUpActionStateFlow.emit(SignUpActionState.Loading)
        try {
            when (val response = authInteractor.signInWithEmail(email, password)) {
                is NetworkResponse.Success -> {
                    _signUpActionStateFlow.emit(SignUpActionState.Pending)
                }
                is NetworkResponse.ServerError -> {
                    _signUpActionStateFlow.emit(SignUpActionState.ServerSignInError(response))
                }
                is NetworkResponse.NetworkError -> {
                    _signUpActionStateFlow.emit(SignUpActionState.NetworkError(response))
                }
                is NetworkResponse.UnknownError -> {
                    _signUpActionStateFlow.emit(SignUpActionState.UnknownError(response))
                }
            }
        } catch (error: Throwable) {
            Timber.e(error)
            _signUpActionStateFlow.emit(SignUpActionState.UnknownError(NetworkResponse.UnknownError(error)))
        }
    }

    fun signUp(firstName: String,
               lastName: String,
               username: String,
               email: String,
               password: String) {
        viewModelScope.launch {
            _signUpActionStateFlow.emit(SignUpActionState.Loading)
            try {
                when (val response = authInteractor.signUpWithPersonalInfo(firstName, lastName, username, email, password)) {
                    is NetworkResponse.Success -> {
                        _signUpActionStateFlow.emit(SignUpActionState.Pending)
                        signIn(email, password)
                    }
                    is NetworkResponse.ServerError -> {
                        _signUpActionStateFlow.emit(SignUpActionState.ServerError(response))
                    }
                    is NetworkResponse.NetworkError -> {
                        _signUpActionStateFlow.emit(SignUpActionState.NetworkError(response))
                    }
                    is NetworkResponse.UnknownError -> {
                        _signUpActionStateFlow.emit(SignUpActionState.UnknownError(response))
                    }
                }
            } catch (error: Throwable) {
                Timber.e(error)
                _signUpActionStateFlow.emit(SignUpActionState.UnknownError(NetworkResponse.UnknownError(error)))
            }

        }
    }

    sealed class SignUpActionState {
        object Pending : SignUpActionState()
        object Loading : SignUpActionState()
        data class ServerError(val e: NetworkResponse.ServerError<CreateProfileErrorResponse>) : SignUpActionState()
        data class NetworkError(val e: NetworkResponse.NetworkError) : SignUpActionState()
        data class ServerSignInError(val e: NetworkResponse.ServerError<SignInWithEmailErrorResponse>) : SignUpActionState()
        data class UnknownError(val e: NetworkResponse.UnknownError) : SignUpActionState()
    }
}