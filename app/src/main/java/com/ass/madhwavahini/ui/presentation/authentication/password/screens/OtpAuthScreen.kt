package com.ass.madhwavahini.ui.presentation.authentication.password.screens

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.R
import com.ass.madhwavahini.ui.presentation.authentication.common.PlaceHolderLoading
import com.ass.madhwavahini.ui.presentation.authentication.password.ResetPasswordViewModel
import com.ass.madhwavahini.ui.presentation.authentication.password.components.OtpView
import com.ass.madhwavahini.ui.theme.dimens

@Composable
fun OtpAuthScreen(
    viewModel: ResetPasswordViewModel
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(0.9f),
            style = MaterialTheme.typography.titleLarge,
            text = "Enter your 6 digit otp to verify",
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(20.dp))
        OtpView(
            otpText = viewModel.otpText,
            otpError = viewModel.otpError,
            onOtpTextChange = viewModel::setOtp,
            onDoneClick = viewModel::verifyOtp
        )
        Spacer(modifier = Modifier.height(30.dp))
        AnimatedContent(targetState = viewModel.isLoading, transitionSpec = {
            fadeIn(animationSpec = tween(durationMillis = 300)) togetherWith fadeOut(
                animationSpec = tween(durationMillis = 300)
            )
        }, label = "loading") { loading ->
            if (loading) {
                PlaceHolderLoading()
            } else {
                Button(
                    onClick = viewModel::verifyOtp,
                    modifier = Modifier
                        .height(MaterialTheme.dimens.buttonSize)
                        .wrapContentWidth()
                ) {
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