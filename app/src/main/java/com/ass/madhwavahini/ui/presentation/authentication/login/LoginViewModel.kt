package com.ass.madhwavahini.ui.presentation.authentication.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.madhwavahini.data.local.UserDataStore
import com.ass.madhwavahini.domain.repository.LoginRepository
import com.ass.madhwavahini.ui.presentation.authentication.model.LoginState
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
    private val loginRepository: LoginRepository,
    private val userDataStore: UserDataStore
) : ViewModel() {

    private val _mobile = MutableStateFlow("")
    val mobile get() = _mobile.asStateFlow()

    private val _mobileError = MutableStateFlow<String?>(null)
    val mobileError get() = _mobileError.asStateFlow()


    private val _password = MutableStateFlow("")
    val password get() = _password.asStateFlow()

    private val _passwordError = MutableStateFlow<String?>(null)
    val passwordError get() = _passwordError.asStateFlow()

    var isLoading by mutableStateOf(false)
        private set

    private val _loginState = Channel<LoginState>()
    val loginState get() = _loginState.receiveAsFlow()

    init {
        viewModelScope.launch {
            userDataStore.getToken()?.let { token ->
                loginRepository.verifyUser(token).collectLatest {
                    isLoading = it.isLoading
                    _loginState.send(it)
                }
            }
        }
    }

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
            loginRepository.loginUser(mobile, password).collectLatest {
                isLoading = it.isLoading
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


}