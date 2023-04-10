package com.ass.muktimargadarshini.ui.presentation.authentication.model

sealed class LoginState {
    object Idle : LoginState()
    object InvalidMobile : LoginState()
    object LoginSuccessful : LoginState()
    object OtpVerifier : LoginState()
}
