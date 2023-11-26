package com.ass.madhwavahini.ui.presentation.authentication.password.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.R
import com.ass.madhwavahini.ui.presentation.authentication.password.ResetPasswordViewModel
import com.ass.madhwavahini.ui.presentation.authentication.password.components.OtpView

@Composable
fun OtpAuthScreen(
    viewModel: ResetPasswordViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        OtpView(
            otpText = viewModel.otpText,
            otpError = viewModel.otpError,
            onOtpTextChange = viewModel::setOtp
        )
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
                    viewModel.verifyOtp()
                }) {
                    Text(
                        text = stringResource(id = R.string.verify_otp),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }

            }
        }
    }
}