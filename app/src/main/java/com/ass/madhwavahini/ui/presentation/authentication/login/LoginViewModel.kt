package com.ass.madhwavahini.ui.presentation.authentication.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ass.madhwavahini.data.local.UserDataStore
import com.ass.madhwavahini.domain.modals.User
import com.ass.madhwavahini.domain.repository.LoginRepository
import com.ass.madhwavahini.domain.wrapper.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val userDataStore: UserDataStore
) : ViewModel() {

    var mobileText by mutableStateOf("")
        private set
    var mobileError by mutableStateOf<String?>(null)
        private set
    var passwordText by mutableStateOf("")
        private set
    var passwordError by mutableStateOf<String?>("")
        private set
    var isLoading by mutableStateOf(false)
        private set

    private val _userState = Channel<UiState<User>>()
    val userState get() = _userState.receiveAsFlow()

    init {
        viewModelScope.launch {
            userDataStore.getToken()?.let { token ->
                loginRepository.verifyUser(token).collectLatest {
                    isLoading = it.isLoading
                    _userState.send(it)
                }
            }
        }
    }

    fun login(mobile: String, password: String) {
        viewModelScope.launch {
            if (mobile.isEmpty()) {
                mobileError = "Mobile number required."
                return@launch
            }
            if (mobile.length < 10 || !mobile.all { char -> char.isDigit() } || mobile[0] == '0') {
                mobileError = "Invalid mobile number."
                return@launch
            }
            mobileError = null
            if (password.isEmpty()) {
                passwordError = "Password required."
                return@launch
            }
            passwordError = null
            loginRepository.loginUser(mobile, password).collectLatest {
                isLoading = it.isLoading
                _userState.send(it)
            }
        }
    }

    fun setMobile(newValue: String) {
        mobileText = newValue
    }

    fun setPassword(newValue: String) {
        passwordText = newValue
    }


}