package com.ass.madhwavahini.ui.presentation.authentication.password

import android.app.Activity
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.rememberAsyncImagePainter
import com.ass.madhwavahini.R
import com.ass.madhwavahini.ui.presentation.authentication.common.MobileInput
import com.ass.madhwavahini.ui.presentation.authentication.password.components.OtpTextField
import com.ass.madhwavahini.ui.presentation.common.MyCustomSnack
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthMissingActivityForRecaptchaException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

@Composable
fun MobileVerificationScreen(
    viewModel: MobileVerificationViewModel = hiltViewModel(),
    onMobileVerified: (phone: String) -> Unit
) {

    val focusRequester: FocusRequester = remember { FocusRequester() }
    val focusManager: FocusManager = LocalFocusManager.current
    val ctx = LocalContext.current
    val snackBarHostState = remember { SnackbarHostState() }
    val lifecycleOwner = LocalLifecycleOwner.current

    Scaffold(snackbarHost = {
        SnackbarHost(
            hostState = snackBarHostState
        ) { sb: SnackbarData ->
            MyCustomSnack(
                text = sb.visuals.message
            ) {
                snackBarHostState.currentSnackbarData?.dismiss()
            }
        }
    }) { padding ->
        val res = LocalContext.current.resources
        LaunchedEffect(key1 = lifecycleOwner.lifecycle) {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                /* viewModel.loginState.collect { login ->
                     login.data?.let {
                         onNavigate()
                         snackBarHostState.showSnackbar(
                             message = res.getString(R.string.login_success),
                             duration = SnackbarDuration.Short
                         )
                     }
                     login.error?.let {
                         snackBarHostState.showSnackbar(
                             message = it.asString(ctx),
                             duration = SnackbarDuration.Short
                         )
                     }
                 }*/
            }
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Image(
                modifier = Modifier.size(190.dp),
                contentScale = ContentScale.Crop,
                painter = rememberAsyncImagePainter(model = R.drawable.app_logo),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(50.dp))
            MobileInput(
                focusManager = focusManager,
                focusRequester = focusRequester,
                mobile = viewModel.mobileText,
                error = viewModel.mobileError,
                onValueChanged = viewModel::setMobile
            )
            OtpTextField(
                otpText = viewModel.otpText,
                onOtpTextChange = viewModel::setOtp
            )
            Spacer(modifier = Modifier.height(5.dp))

            Spacer(modifier = Modifier.height(50.dp))
            AnimatedContent(targetState = viewModel.isLoading, transitionSpec = {
                fadeIn(animationSpec = tween(durationMillis = 300)) togetherWith fadeOut(
                    animationSpec = tween(durationMillis = 300)
                )
            }, label = "loading") { loading ->
                if (loading) {
                    CircularProgressIndicator()
                } else {
                    Button(onClick = {

                    }) {
                        Text(
                            text = stringResource(id = R.string.login),
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                }
            }
            Spacer(modifier = Modifier.height(50.dp))
        }
    }
}

private fun Activity.getOtp(
    phoneNumber: String
) {
    val auth = FirebaseAuth.getInstance()
    auth.useAppLanguage()

    val callback = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // This callback will be invoked in two situations:
            // 1 - Instant verification. In some cases the phone number can be instantly
            //     verified without needing to send or enter a verification code.
            // 2 - Auto-retrieval. On some devices Google Play services can automatically
            //     detect the incoming verification SMS and perform verification without
            //     user action.
            // Log.d(TAG, "onVerificationCompleted:$credential")
            // signInWithPhoneAuthCredential(credential)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            when (e) {
                is FirebaseAuthInvalidCredentialsException -> {
                    // Invalid request
                }

                is FirebaseTooManyRequestsException -> {
                    // The SMS quota for the project has been exceeded
                }

                is FirebaseAuthMissingActivityForRecaptchaException -> {
                    // reCAPTCHA verification attempted with null Activity
                }
            }

            // Show a message and update the UI
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken,
        ) {
            // The SMS verification code has been sent to the provided phone number, we
            // now need to ask the user to enter the code and then construct a credential
            // by combining the code with a verification ID.
            //  Log.d(TAG, "onCodeSent:$verificationId")

            // Save verification ID and resending token so we can use them later
            // storedVerificationId = verificationId
            // resendToken = token
        }
    }
    val options =
        PhoneAuthOptions.newBuilder(auth).setPhoneNumber(phoneNumber) // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this) // Activity (for callback binding)
            .setCallbacks(callback) // OnVerificationStateChangedCallbacks
            .build()
    PhoneAuthProvider.verifyPhoneNumber(options)
}

private fun Activity.verifyOtp(
    verificationId: String, otp: String, onMobileVerified: (phone: String) -> Unit
) {
    val credential = PhoneAuthProvider.getCredential(verificationId, otp)
    FirebaseAuth.getInstance().signInWithCredential(credential)
        .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, update UI with the signed-in user's information
                // Log.d(TAG, "signInWithCredential:success")
                val user = task.result?.user
                onMobileVerified(user?.phoneNumber!!)
            } else {
                // Sign in failed, display a message and update the UI
                //  Log.w(TAG, "signInWithCredential:failure", task.exception)
                if (task.exception is FirebaseAuthInvalidCredentialsException) {
                    // The verification code entered was invalid
                }
                // Update UI
            }
        }
}