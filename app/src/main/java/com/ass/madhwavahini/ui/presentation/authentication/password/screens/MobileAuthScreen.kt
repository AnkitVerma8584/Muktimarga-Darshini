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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.R
import com.ass.madhwavahini.ui.presentation.authentication.common.MobileInput
import com.ass.madhwavahini.ui.presentation.authentication.password.ResetPasswordViewModel

@Composable
fun MobileAuthScreen(
    viewModel: ResetPasswordViewModel
) {
    val focusRequester: FocusRequester = remember { FocusRequester() }
    val focusManager: FocusManager = LocalFocusManager.current
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MobileInput(
            focusManager = focusManager,
            focusRequester = focusRequester,
            mobile = viewModel.mobileText,
            error = viewModel.mobileError,
            onValueChanged = viewModel::setMobile,
            imeAction = ImeAction.Done
        )
        Spacer(modifier = Modifier.height(50.dp))
        AnimatedContent(
            targetState = viewModel.isLoading, transitionSpec = {
                fadeIn(animationSpec = tween(durationMillis = 300)) togetherWith fadeOut(
                    animationSpec = tween(durationMillis = 300)
                )
            }, label = "loading"
        ) { loading ->
            if (loading) {
                CircularProgressIndicator()
            } else {
                Button(onClick = {
                    viewModel.getUserFromMobile()
                }) {
                    Text(
                        text = stringResource(id = R.string.get_otp),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }

            }
        }
    }
}