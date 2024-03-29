package com.ass.madhwavahini.ui.presentation.authentication.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
class RegisterViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : ViewModel() {

    var nameText by mutableStateOf("")
        private set
    var nameError by mutableStateOf<String?>(null)
        private set
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


    private val _registerState = Channel<UiState<User>>()
    val registerState get() = _registerState.receiveAsFlow()

    fun setName(newValue: String) {
        nameText = newValue
    }

    fun setMobile(newValue: String) {
        mobileText = newValue
    }

    fun setPassword(newValue: String) {
        passwordText = newValue
    }


    fun register() {
        viewModelScope.launch {
            if (nameText.isBlank()) {
                nameError = "Name required."
                return@launch
            }
            if (nameText.length > 50 || nameText.any { char -> char.isDigit() }) {
                nameError = "Invalid name."
                return@launch
            }
            nameError = null
            if (mobileText.isBlank()) {
                mobileError = "Mobile number required."
                return@launch
            }
            if (mobileText.length < 10 || !mobileText.all { char -> char.isDigit() } || mobileText[0] == '0') {
                mobileError = "Invalid mobile number."
                return@launch
            }
            mobileError = null
            if (passwordText.isBlank()) {
                passwordError = "Password required."
                return@launch
            }
            passwordError = null

            loginRepository.registerUser(nameText, mobileText, passwordText).collectLatest {
                isLoading = it.isLoading
                _registerState.send(it)
            }
        }
    }

}