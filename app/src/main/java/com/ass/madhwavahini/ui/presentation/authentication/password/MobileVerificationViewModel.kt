package com.ass.madhwavahini.ui.presentation.authentication.password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.ass.madhwavahini.ui.presentation.authentication.model.LoginState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class MobileVerificationViewModel : ViewModel() {

    var mobileText by mutableStateOf("")
        private set
    var mobileError by mutableStateOf<String?>(null)
        private set
    var otpText by mutableStateOf("")
        private set
    var otpError by mutableStateOf<String?>("")
        private set
    var isLoading by mutableStateOf(false)
        private set

    private val _loginState = Channel<LoginState>()
    val loginState get() = _loginState.receiveAsFlow()

    fun setMobile(newValue: String) {
        mobileText = newValue
    }

    fun setOtp(newValue: String) {
        otpText = newValue
    }
}