package com.ass.madhwavahini.ui.presentation.authentication.password

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.madhwavahini.domain.repository.UserRepository
import com.ass.madhwavahini.domain.wrapper.StringUtil
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private var verificationIdToken: String? = null
    var userId: Int? = null
    var mobileText by mutableStateOf("")
        private set
    var mobileError by mutableStateOf<String?>(null)
        private set

    var otpText by mutableStateOf("")
        private set
    var otpError by mutableStateOf<String?>("")
        private set


    var passwordText by mutableStateOf("")
        private set
    var passwordError by mutableStateOf<String?>(null)
        private set

    var confirmPasswordText by mutableStateOf("")
        private set
    var confirmPasswordError by mutableStateOf<String?>(null)
        private set

    var isLoading by mutableStateOf(false)
        private set

    private val _passwordEvents = Channel<PasswordResetEvents>()
    val passwordEvents get() = _passwordEvents.receiveAsFlow()

    private val _uiState = MutableStateFlow(PasswordScreenState.NUMBER_VERIFICATION)
    val uiState get() = _uiState.asStateFlow()


    fun getUserFromMobile() {
        viewModelScope.launch {
            if (mobileText.isEmpty()) {
                mobileError = "Mobile number required."
                return@launch
            }
            if (mobileText.length < 10 || !mobileText.all { char -> char.isDigit() } || mobileText[0] == '0') {
                mobileError = "Invalid mobile number."
                return@launch
            }
            mobileError = null
            userRepository.getUserId(phone = mobileText).collectLatest {
                isLoading = it.isLoading
                it.data?.let { id ->
                    userId = id
                    _passwordEvents.send(PasswordResetEvents.OnNumberVerified)
                }
                it.error?.let { error ->
                    userId = null
                    _passwordEvents.send(PasswordResetEvents.OnError(error))
                }
            }
        }
    }


    fun verifyOtp() {
        viewModelScope.launch {
            if (otpText.isEmpty()) {
                otpError = "Otp required."
                return@launch
            }
            if (otpText.length < 6 || !mobileText.all { char -> char.isDigit() }) {
                otpError = "Invalid otp"
                return@launch
            }
            otpError = null
            verificationIdToken?.let {
                _passwordEvents.send(PasswordResetEvents.OnOtpVerifyClick(it, otpText))
            } ?: setPasswordEventsError(StringUtil.DynamicText("Some error occurred"))
        }
    }

    fun resetPassword() {
        viewModelScope.launch {
            if (passwordText.isEmpty()) {
                passwordError = "This field is required"
                return@launch
            }
            passwordError = null
            if (confirmPasswordText.isEmpty()) {
                confirmPasswordError = "This field is required"
                return@launch
            }
            if (passwordText != confirmPasswordText) {
                confirmPasswordError = "Password does not match"
                return@launch
            }
            confirmPasswordError = null
            userRepository.setPassword(userId!!, passwordText).collectLatest {
                isLoading = it.isLoading
                it.data?.let { msg ->
                    _passwordEvents.send(PasswordResetEvents.OnPasswordReset(msg))
                }
                it.error?.let { error ->
                    _passwordEvents.send(PasswordResetEvents.OnError(error))
                }
            }
        }
    }

    fun changeToResetScreen() {
        _uiState.value = PasswordScreenState.RESET_PASSWORD
    }

    fun setPasswordEventsError(error: StringUtil) {
        viewModelScope.launch {
            _passwordEvents.send(PasswordResetEvents.OnError(error))
        }
    }

    fun setMobile(newValue: String) {
        mobileText = newValue
    }

    fun setOtp(newValue: String) {
        otpText = newValue
    }

    fun setPassword(newValue: String) {
        passwordText = newValue
    }

    fun setConfirmPassword(newValue: String) {
        confirmPasswordText = newValue
    }

    fun setVerificationId(newValue: String) {
        verificationIdToken = newValue
        _uiState.value = PasswordScreenState.OTP_STATE
    }

}