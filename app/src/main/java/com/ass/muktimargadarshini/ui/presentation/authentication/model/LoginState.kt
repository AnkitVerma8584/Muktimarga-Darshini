package com.ass.muktimargadarshini.ui.presentation.authentication.model

sealed class LoginState {
    data object Idle : LoginState()
    data object InvalidMobile : LoginState()
    data object LoginSuccessful : LoginState()
    data object OtpVerifier : LoginState()
}
