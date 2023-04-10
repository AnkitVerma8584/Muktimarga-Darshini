package com.ass.muktimargadarshini.ui.presentation.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.muktimargadarshini.domain.repository.HomeRepository
import com.ass.muktimargadarshini.ui.presentation.authentication.model.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    homeRepository: HomeRepository
) : ViewModel() {

    private val _mobile = MutableStateFlow("")
    val mobile get() = _mobile.asStateFlow()

    private val _mobileError = MutableStateFlow<String?>(null)
    val mobileError get() = _mobileError.asStateFlow()

    private val _loginState = MutableStateFlow(LoginState.Idle)
    val loginState get() = _loginState.asStateFlow()

    fun getOtp(mobile: String) {
        viewModelScope.launch {
            if (mobile.isEmpty()) {
                _mobileError.value = "Mobile number required."
                return@launch
            }
            if (mobile.length < 10 || !mobile.all { char -> char.isDigit() } || mobile[0] == '0') {
                _mobileError.value = "Invalid mobile number."
                return@launch
            }
            _mobileError.value = null

        }
    }

    fun setMobile(mobile: String) {
        _mobile.value = mobile
    }

}