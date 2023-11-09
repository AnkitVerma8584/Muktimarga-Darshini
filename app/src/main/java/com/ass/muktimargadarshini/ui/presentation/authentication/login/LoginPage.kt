package com.ass.muktimargadarshini.ui.presentation.authentication.login

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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.FilledTonalButton
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
import androidx.compose.ui.layout.ContentScale
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
import com.ass.muktimargadarshini.ui.presentation.authentication.common.PasswordInput
import com.ass.muktimargadarshini.util.UiState
import kotlin.system.exitProcess

@Composable
fun MobileAuthenticationPage(
    onRegisterClicked: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
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
                viewModel.loginState.collect { login ->
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
                .padding(padding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Image(
                modifier = Modifier.size(200.dp),
                contentScale= ContentScale.Crop,
                painter = rememberAsyncImagePainter(model = R.drawable.app_logo),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(50.dp))
            MobileInput(
                focusManager = focusManager,
                focusRequester = focusRequester,
                mobile = mobile,
                error = mobileError,
                onValueChanged = viewModel::setMobile
            )
            PasswordInput(focusManager = focusManager,
                focusRequester = focusRequester,
                password = password,
                passwordError = passwordError,
                onValueChanged = viewModel::setPassword,
                onDoneClicked = { viewModel.login(mobile, password) })

            Spacer(modifier = Modifier.height(5.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = "Don't have an account yet ? ",
                    style = MaterialTheme.typography.labelMedium
                )
                Text(text = "Sign Up",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.clickable {
                        onRegisterClicked.invoke()
                    })
            }
            Spacer(modifier = Modifier.height(50.dp))
            AnimatedContent(targetState = uiState == UiState.Loading, transitionSpec = {
                fadeIn(animationSpec = tween(durationMillis = 300)) togetherWith fadeOut(
                    animationSpec = tween(durationMillis = 300)
                )
            }, label = "loading") { loading ->
                if (loading) {
                    CircularProgressIndicator()
                } else {
                    Button(onClick = {
                        viewModel.login(mobile, password)
                    }) {
                        Text(
                            text = "Login",
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
