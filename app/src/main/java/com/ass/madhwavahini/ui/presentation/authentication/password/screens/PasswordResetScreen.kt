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
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.R
import com.ass.madhwavahini.ui.presentation.authentication.common.PasswordInput
import com.ass.madhwavahini.ui.presentation.authentication.common.PlaceHolderLoading
import com.ass.madhwavahini.ui.presentation.authentication.password.ResetPasswordViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun ResetPasswordModule(
    viewModel: ResetPasswordViewModel
) {
    val focusRequester: FocusRequester = remember { FocusRequester() }
    val focusManager: FocusManager = LocalFocusManager.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Reset your password",
            modifier = Modifier.fillMaxWidth(0.9f),
            style = MaterialTheme.typography.titleLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(10.dp))
        PasswordInput(
            focusRequester = focusRequester,
            focusManager = focusManager,
            password = viewModel.passwordText,
            passwordError = viewModel.passwordError,
            onValueChanged = viewModel::setPassword,
            imeAction = ImeAction.Next,
            label = "Password",
            hint = "Enter a new password",
        )
        PasswordInput(
            focusRequester = focusRequester,
            focusManager = focusManager,
            password = viewModel.confirmPasswordText,
            passwordError = viewModel.confirmPasswordError,
            onDoneClicked = viewModel::resetPassword,
            onValueChanged = viewModel::setConfirmPassword,
            label = "Confirm Password",
            hint = "Confirm your new password",
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
                PlaceHolderLoading()
            } else {
                Button(onClick = viewModel::resetPassword) {
                    Text(
                        text = stringResource(id = R.string.reset_password),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }

            }
        }
    }
}