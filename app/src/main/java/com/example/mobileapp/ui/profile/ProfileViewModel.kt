package com.example.mobileapp.ui.profile

import androidx.lifecycle.viewModelScope
import com.example.mobileapp.domain.User
import com.example.mobileapp.interactor.AuthInteractor
import com.example.mobileapp.interactor.ProfileInteractor
import com.example.mobileapp.ui.base.BaseViewModel
import com.example.mobileapp.ui.userlist.UserListViewModel
import com.haroldadmin.cnradapter.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authInteractor: AuthInteractor,
    private val profileInteractor: ProfileInteractor
) : BaseViewModel() {

    private val _eventChannel = Channel<Event>(Channel.BUFFERED)
    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewState: Flow<ViewState> get() = _viewState.asStateFlow()


    fun eventsFlow() : Flow<Event> {
        return _eventChannel.receiveAsFlow()
    }

    fun logout() {
        viewModelScope.launch {
            try {
                authInteractor.logout()
            } catch (error : Throwable) {
                Timber.e(error)
                _eventChannel.send(Event.LogoutError(error))
            }
        }
    }

    init {
        getProfile()
    }

    fun getProfile() {
        viewModelScope.launch {
            _viewState.emit(ViewState.Loading)
            when (val response = profileInteractor.getProfile()) {
                is NetworkResponse.Success -> {
                    _viewState.emit(ViewState.Data(response.body))
                }
                else -> {
                    // TODO: handle errors
                }
            }

        }
    }

    sealed class Event {
        data class LogoutError(val error: Throwable) : Event()
    }

    sealed class ViewState {
        object Loading : ViewState()
        data class Data(val user: User) : ViewState()
    }
}
