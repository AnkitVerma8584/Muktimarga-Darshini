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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import com.ass.madhwavahini.R
import com.ass.madhwavahini.ui.presentation.authentication.common.MobileInput
import com.ass.madhwavahini.ui.presentation.authentication.common.NameInput
import com.ass.madhwavahini.ui.presentation.authentication.common.PasswordInput
import com.ass.madhwavahini.ui.presentation.common.MyCustomSnack

@Composable
fun RegisterPage(
    sharedImage: @Composable () -> Unit,
    onSignInClick: () -> Unit,
    onNavigate: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
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
                viewModel.registerState.collect { login ->

                    login.data?.let {
                        onNavigate()
                        snackBarHostState.showSnackbar(
                            message = res.getString(R.string.signin_success),
                            duration = SnackbarDuration.Short
                        )
                    }
                    login.error?.let {
                        snackBarHostState.showSnackbar(
                            message = it.asString(ctx),
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            sharedImage()
            Spacer(modifier = Modifier.height(50.dp))
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
            PasswordInput(focusManager = focusManager,
                focusRequester = focusRequester,
                password = viewModel.passwordText,
                passwordError = viewModel.passwordError,
                onValueChanged = viewModel::setPassword,
                onDoneClicked = {
                    viewModel.register(
                        viewModel.nameText,
                        viewModel.mobileText,
                        viewModel.passwordText
                    )
                })

            Spacer(modifier = Modifier.height(5.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = R.string.has_account),
                    style = MaterialTheme.typography.labelMedium
                )
                Text(text = stringResource(id = R.string.register),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.clickable {
                        onSignInClick.invoke()
                    })
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
                    Button(onClick = {
                        viewModel.register(
                            viewModel.nameText,
                            viewModel.mobileText, viewModel.passwordText
                        )
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

