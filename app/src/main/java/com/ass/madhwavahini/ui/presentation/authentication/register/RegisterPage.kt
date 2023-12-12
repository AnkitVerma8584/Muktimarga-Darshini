package com.ass.madhwavahini.ui.presentation.authentication.register

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.ass.madhwavahini.R
import com.ass.madhwavahini.ui.presentation.authentication.common.MobileInput
import com.ass.madhwavahini.ui.presentation.authentication.common.NameInput
import com.ass.madhwavahini.ui.presentation.authentication.common.PasswordInput
import com.ass.madhwavahini.ui.presentation.common.SnackBarType

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RegisterPage(
    showSnack: (message: String, snackBarType: SnackBarType) -> Unit,
    onSignInClick: () -> Unit,
    onNavigate: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {
    val focusRequester: FocusRequester = remember { FocusRequester() }
    val focusManager: FocusManager = LocalFocusManager.current
    val ctx = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val res = LocalContext.current.resources
    LaunchedEffect(key1 = lifecycleOwner.lifecycle) {
        lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.registerState.collect { register ->
                register.data?.let {
                    onNavigate()
                    showSnack(
                        res.getString(R.string.signin_success),
                        SnackBarType.NORMAL
                    )
                }
                register.error?.let {
                    showSnack(
                        it.asString(ctx),
                        SnackBarType.ERROR
                    )
                }
            }
        }
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NameInput(
            focusManager = focusManager,
            focusRequester = focusRequester,
            name = viewModel.nameText,
            error = viewModel.nameError,
            onValueChanged = viewModel::setName
        )
        MobileInput(
            focusManager = focusManager,
            focusRequester = focusRequester,
            mobile = viewModel.mobileText,
            error = viewModel.mobileError,
            onValueChanged = viewModel::setMobile
        )
        PasswordInput(
            focusManager = focusManager,
            focusRequester = focusRequester,
            password = viewModel.passwordText,
            passwordError = viewModel.passwordError,
            onValueChanged = viewModel::setPassword,
            onDoneClicked = viewModel::register
        )

        Spacer(modifier = Modifier.height(5.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = stringResource(id = R.string.has_account),
                style = MaterialTheme.typography.labelMedium
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = stringResource(id = R.string.login),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.clickable(
                    role = Role.Button,
                    enabled = !viewModel.isLoading,
                    onClick = onSignInClick
                )
            )
        }
        Spacer(modifier = Modifier.height(50.dp))
        AnimatedContent(targetState = viewModel.isLoading, transitionSpec = {
            fadeIn(animationSpec = tween(durationMillis = 300)) togetherWith fadeOut(
                animationSpec = tween(durationMillis = 300)
            )
        }, label = "loading") { loading ->
            if (loading) {
                CircularProgressIndicator()
            } else {
                Button(onClick = viewModel::register) {
                    Text(
                        text = stringResource(id = R.string.register),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(50.dp))
    }

}

