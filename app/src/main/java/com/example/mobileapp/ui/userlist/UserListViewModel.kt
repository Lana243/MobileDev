package com.example.mobileapp.ui.userlist

import androidx.lifecycle.viewModelScope
import com.example.mobileapp.Api
import com.example.mobileapp.BuildConfig
import com.example.mobileapp.data.network.MockApi
import com.example.mobileapp.data.network.interceptor.AuthorizationInterceptor
import com.example.mobileapp.domain.User
import com.example.mobileapp.repository.AuthRepository
import com.example.mobileapp.ui.base.BaseViewModel
import com.squareup.moshi.Moshi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

class UserListViewModel : BaseViewModel() {

    sealed class ViewState {
        object Loading : ViewState()
        data class Data(val userList: List<User>) : ViewState()
    }

    private val _viewState = MutableStateFlow<ViewState>(ViewState.Loading)
    val viewState: Flow<ViewState> get() = _viewState.asStateFlow()

    init {
        viewModelScope.launch {
            _viewState.emit(ViewState.Loading)
            val users = loadUsers()
            _viewState.emit(ViewState.Data(users))
        }
    }

    private suspend fun loadUsers(): List<User> {
        return withContext(Dispatchers.IO) {
            Thread.sleep(3000)
            provideApi().getUsers().data
        }
    }

    private fun provideApi(): Api {
        /*if () {
            return MockApi()
        }*/
        return Retrofit.Builder()
            .client(provideOkHttpClient())
            .baseUrl("https://reqres.in/api/")
            .addConverterFactory(MoshiConverterFactory.create(provideMoshi()))
            .build()
            .create(Api::class.java)
    }

    private fun provideOkHttpClient(): OkHttpClient =
        OkHttpClient
            .Builder()
            //.addInterceptor(AuthorizationInterceptor(AuthRepository(api, ...)))
            //.authenticator(MobileAppAuthenticator(AuthRepository(api, ...)))
            .apply {
                if (BuildConfig.DEBUG) {
                    addNetworkInterceptor(
                        HttpLoggingInterceptor { message ->
                            Timber.d(message)
                        }.setLevel(HttpLoggingInterceptor.Level.BODY)
                    )
                }
            }
            .build()

    private fun provideMoshi(): Moshi {
        return Moshi.Builder().build()
    }
}