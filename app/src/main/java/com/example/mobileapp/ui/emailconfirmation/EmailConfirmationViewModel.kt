package com.example.mobileapp.ui.emailconfirmation

import android.os.CountDownTimer
import android.widget.Toast
import androidx.lifecycle.viewModelScope
import com.example.mobileapp.ui.base.BaseViewModel
import com.example.mobileapp.ui.signup.SignUpViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class EmailConfirmationViewModel @Inject constructor() : BaseViewModel() {

    sealed class VerificationState {
        object Pending : VerificationState()
        object Verified : VerificationState()
        data class VerificationError(val message: String) : VerificationState()
        class TimerTicking(val code : String, val timeRemainSeconds: Long) : VerificationState()
    }

    private val _verificationStateFlow = MutableStateFlow<VerificationState>(VerificationState.Pending)
    private var _secretCode = "123456"
    private val _randomizer = Random(1337)

    fun sendCode() {
        _secretCode = _randomizer.nextInt(100000, 999999).toString()
        object : CountDownTimer(10000, 1000) {
                override fun onTick(msRemain: Long) {
                    viewModelScope.launch {
                        _verificationStateFlow.emit(
                            VerificationState.TimerTicking(
                                _secretCode,
                                TimeUnit.MILLISECONDS.toSeconds(msRemain)
                            )
                        )
                    }
                }

                override fun onFinish() {
                    viewModelScope.launch {
                        _verificationStateFlow.emit(VerificationState.Pending)
                    }
                }
            }.start()
    }

    fun verificationStateFlow(): Flow<VerificationState> {
        return _verificationStateFlow.asStateFlow()
    }

    fun verifyCode(code : String?) {
        viewModelScope.launch {
            if (code == null) {
                _verificationStateFlow.emit(VerificationState.VerificationError("Неверный код"))
            } else {
                if (code == _secretCode)
                    _verificationStateFlow.emit(VerificationState.Verified)
                else
                    _verificationStateFlow.emit(VerificationState.VerificationError("Неверный код"))
            }
        }
    }
}
