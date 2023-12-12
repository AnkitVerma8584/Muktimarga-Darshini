package com.ass.madhwavahini.ui.presentation.authentication.password

import com.ass.madhwavahini.domain.wrapper.StringUtil

//One time events
sealed class PasswordResetEvents {
    data class OnError(val error: StringUtil) : PasswordResetEvents()
    data object OnNumberVerified : PasswordResetEvents()

    data class OnOtpVerifyClick(
        val verificationId: String,
        val otp: String,
    ) : PasswordResetEvents()

    data object OnOtpVerified : PasswordResetEvents()
    data class OnPasswordReset(val message: String) : PasswordResetEvents()
}

//Permanent states
enum class PasswordScreenState {
    NUMBER_VERIFICATION,
    OTP_STATE,
    RESET_PASSWORD
}