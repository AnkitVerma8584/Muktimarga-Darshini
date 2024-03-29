package com.ass.madhwavahini.ui.presentation.authentication.password

import android.app.Activity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.ass.madhwavahini.domain.wrapper.StringUtil
import com.ass.madhwavahini.ui.presentation.authentication.password.PasswordScreenState.NUMBER_VERIFICATION
import com.ass.madhwavahini.ui.presentation.authentication.password.PasswordScreenState.OTP_STATE
import com.ass.madhwavahini.ui.presentation.authentication.password.PasswordScreenState.RESET_PASSWORD
import com.ass.madhwavahini.ui.presentation.authentication.password.screens.MobileAuthScreen
import com.ass.madhwavahini.ui.presentation.authentication.password.screens.OtpAuthScreen
import com.ass.madhwavahini.ui.presentation.authentication.password.screens.ResetPasswordModule
import com.ass.madhwavahini.ui.presentation.common.SnackBarType
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.flow.collectLatest
import java.util.concurrent.TimeUnit

@Composable
fun Activity.ResetPasswordFragment(
    showSnack: (message: String, snackBarType: SnackBarType) -> Unit,
    viewModel: ResetPasswordViewModel = hiltViewModel(),
    onPasswordResetCompleted: () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val ctx = LocalContext.current
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(key1 = lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.passwordEvents.collectLatest { events ->

                when (events) {
                    is PasswordResetEvents.OnError -> {
                        showSnack(
                            events.error.asString(ctx),
                            SnackBarType.ERROR
                        )
                    }

                    PasswordResetEvents.OnNumberVerified -> {
                        viewModel.setLoadingState(true)
                        getOtp(
                            phoneNumber = viewModel.mobileText,
                            onCodeSent = viewModel::setVerificationId,
                            onVerified = viewModel::changeToResetScreen,
                            onError = viewModel::setPasswordEventsError
                        )
                    }

                    is PasswordResetEvents.OnOtpVerifyClick -> {
                        viewModel.setLoadingState(true)
                        verifyOtp(
                            events.verificationId,
                            events.otp,
                            onMobileVerified = viewModel::changeToResetScreen,
                            onError = viewModel::setPasswordEventsError
                        )
                    }

                    PasswordResetEvents.OnOtpVerified -> viewModel.changeToResetScreen()

                    is PasswordResetEvents.OnPasswordReset -> {
                        showSnack(
                            events.message,
                            SnackBarType.NORMAL
                        )
                        onPasswordResetCompleted()
                    }
                }
            }
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        AnimatedContent(
            targetState = uiState, transitionSpec = {
                fadeIn(animationSpec = tween(durationMillis = 300)) togetherWith fadeOut(
                    animationSpec = tween(durationMillis = 300)
                )
            }, label = "loading"
        ) { state ->
            when (state) {
                NUMBER_VERIFICATION -> MobileAuthScreen(viewModel = viewModel)
                OTP_STATE -> OtpAuthScreen(viewModel = viewModel)
                RESET_PASSWORD -> ResetPasswordModule(viewModel = viewModel)
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
    }
}


private fun Activity.getOtp(
    phoneNumber: String,
    onCodeSent: (String) -> Unit,
    onVerified: () -> Unit,
    onError: (StringUtil) -> Unit
) {
    val auth = FirebaseAuth.getInstance()
    auth.useAppLanguage()

    val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            onVerified()
        }

        override fun onVerificationFailed(e: FirebaseException) {
            when (e) {
                is FirebaseAuthInvalidCredentialsException -> {
                    // Invalid request
                    onError(StringUtil.DynamicText("Invalid request, please check your phone number"))
                }

                is FirebaseTooManyRequestsException -> {
                    // The SMS quota for the project has been exceeded
                    onError(StringUtil.DynamicText("Too many tries! Please try again after sometime."))
                }

                is FirebaseAuthMissingActivityForRecaptchaException -> {
                    // reCAPTCHA verification attempted with null Activity
                    onError(StringUtil.DynamicText("reCAPTCHA failed. Please try again."))
                }

                else -> onError(StringUtil.DynamicText("Some unknown error occurred."))
            }

        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            onCodeSent(verificationId)
        }
    }

    val options = PhoneAuthOptions.newBuilder(auth)
        .setPhoneNumber("+91$phoneNumber") // Phone number to verify
        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
        .setActivity(this) // Activity (for callback binding)
        .setCallbacks(callback) // OnVerificationStateChangedCallbacks
        .build()
    PhoneAuthProvider.verifyPhoneNumber(options)
}

private fun Activity.verifyOtp(
    verificationId: String?,
    otp: String,
    onMobileVerified: () -> Unit,
    onError: (error: StringUtil) -> Unit
) {
    if (verificationId == null) return

    val credential = PhoneAuthProvider.getCredential(verificationId, otp)
    FirebaseAuth.getInstance().signInWithCredential(credential)
        .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                onMobileVerified()
            } else {
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    onError(StringUtil.DynamicText("Invalid otp"))
                    return@addOnCompleteListener
                }
                onError(StringUtil.DynamicText("Something went wrong"))
            }
        }
}