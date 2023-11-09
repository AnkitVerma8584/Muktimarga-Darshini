package com.ass.muktimargadarshini.ui.presentation.authentication.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.muktimargadarshini.domain.repository.UserRepository
import com.ass.muktimargadarshini.ui.presentation.authentication.model.LoginState
import com.ass.muktimargadarshini.util.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _mobile = MutableStateFlow("")
    val mobile get() = _mobile.asStateFlow()

    private val _mobileError = MutableStateFlow<String?>(null)
    val mobileError get() = _mobileError.asStateFlow()


    private val _password = MutableStateFlow("")
    val password get() = _password.asStateFlow()

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError get() = _passwordError.asStateFlow()

    private val _uiState = MutableStateFlow<UiState>(UiState.Idle)
    val uiState get() = _uiState.asStateFlow()

    private val _loginState = Channel<LoginState>()
    val loginState get() = _loginState.receiveAsFlow()

    fun login(mobile: String, password: String) {
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
            if (password.isEmpty()) {
                _passwordError.value = "Password required."
                return@launch
            }
            _passwordError.value = null
            userRepository.loginUser(mobile, password).collectLatest {
                _uiState.value = if (it.isLoading) UiState.Loading else UiState.Idle
                _loginState.send(it)
            }
        }
    }

    fun setMobile(mobile: String) {
        _mobile.value = mobile
    }

    fun setPassword(password: String) {
        _password.value = password
    }


    fun register(name: String, mobile: String, password: String) {
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
            if (password.isEmpty()) {
                _passwordError.value = "Password required."
                return@launch
            }
            _passwordError.value = null

            userRepository.registerUser(name, mobile, password).collectLatest {
                //_loginState.value = it
            }
        }
    }

}