package com.ass.muktimargadarshini.ui.presentation.authentication.register

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.rememberAsyncImagePainter
import com.ass.muktimargadarshini.R
import com.ass.muktimargadarshini.ui.presentation.authentication.common.MobileInput
import com.ass.muktimargadarshini.ui.presentation.authentication.common.NameInput
import com.ass.muktimargadarshini.ui.presentation.authentication.common.PasswordInput
import com.ass.muktimargadarshini.util.UiState
import kotlin.system.exitProcess

@Composable
fun RegisterPage(
    onSignInClick: () -> Unit,
    viewModel: RegisterViewModel = hiltViewModel()
) {

    val name by viewModel.name.collectAsStateWithLifecycle()
    val nameError by viewModel.nameError.collectAsStateWithLifecycle()

    val mobile by viewModel.mobile.collectAsStateWithLifecycle()
    val mobileError by viewModel.mobileError.collectAsStateWithLifecycle()

    val password by viewModel.password.collectAsStateWithLifecycle()
    val passwordError by viewModel.passwordError.collectAsStateWithLifecycle()

    var backPressedTime by remember { mutableLongStateOf(0L) }
    val context = LocalContext.current

    val focusRequester: FocusRequester = remember { FocusRequester() }
    val focusManager: FocusManager = LocalFocusManager.current
    val ctx = LocalContext.current

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val lifecycleOwner = LocalLifecycleOwner.current

    Scaffold(snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    }) { padding ->

        LaunchedEffect(key1 = lifecycleOwner.lifecycle) {
            lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.registerState.collect { login ->
                    login.error?.let {
                        snackbarHostState.showSnackbar(
                            message = it.asString(ctx),
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            }
        }

        BackHandler(onBack = {
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                exitProcess(0)
            } else {
                Toast.makeText(context, "Press back again to exit", Toast.LENGTH_SHORT).show()
                backPressedTime = System.currentTimeMillis()
            }
        })

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(300.dp),
                painter = rememberAsyncImagePainter(model = R.drawable.app_logo),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(20.dp))
            NameInput(
                focusManager = focusManager,
                focusRequester = focusRequester,
                name = name,
                error = nameError,
                onValueChanged = viewModel::setName
            )
            Spacer(modifier = Modifier.height(5.dp))
            MobileInput(
                focusManager = focusManager,
                focusRequester = focusRequester,
                mobile = mobile,
                error = mobileError,
                onValueChanged = viewModel::setMobile
            )
            Spacer(modifier = Modifier.height(5.dp))
            PasswordInput(focusManager = focusManager,
                focusRequester = focusRequester,
                password = password,
                passwordError = passwordError,
                onValueChanged = viewModel::setPassword,
                onDoneClicked = { viewModel.register(name, mobile, password) })

            Spacer(modifier = Modifier.height(5.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Already have an account? ",
                    style = MaterialTheme.typography.labelMedium
                )
                Text(text = "Sign In",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.clickable {
                        onSignInClick.invoke()
                    })
            }
            Spacer(modifier = Modifier.height(30.dp))
            AnimatedContent(targetState = uiState == UiState.Loading, transitionSpec = {
                fadeIn(animationSpec = tween(durationMillis = 300)) togetherWith fadeOut(
                    animationSpec = tween(durationMillis = 300)
                )
            }, label = "loading") { loading ->
                if (loading) {
                    CircularProgressIndicator()
                } else {
                    Button(onClick = {
                        viewModel.register(name, mobile, password)
                    }) {
                        Text(
                            text = "Login", style = MaterialTheme.typography.bodyLarge
                        )
                    }

                }
            }

        }
    }
}

