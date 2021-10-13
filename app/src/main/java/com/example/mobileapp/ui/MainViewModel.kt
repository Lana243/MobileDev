package com.example.mobileapp.ui

import com.example.mobileapp.ui.base.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow


class MainViewModel : BaseViewModel() {
    val isAuthorizedFlow: Flow<Boolean> = MutableStateFlow(true)
}