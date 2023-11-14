package com.ass.madhwavahini.ui.presentation.authentication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ass.madhwavahini.data.Constants.SPLASH_TIMEOUT
import com.ass.madhwavahini.ui.presentation.MainActivity
import com.ass.madhwavahini.ui.presentation.SplashScreen
import com.ass.madhwavahini.ui.presentation.authentication.login.MobileAuthenticationPage
import com.ass.madhwavahini.ui.presentation.authentication.register.RegisterPage
import com.ass.madhwavahini.ui.theme.MuktimargaDarshiniTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class AuthenticationActivity : ComponentActivity() {

    private val viewModel: AuthenticationViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var splashState by rememberSaveable {
                mutableStateOf(true)
            }
            LaunchedEffect(key1 = true) {
                delay(SPLASH_TIMEOUT)
                splashState = false
            }
            val lifecycleOwner = LocalLifecycleOwner.current
            LaunchedEffect(key1 = lifecycleOwner.lifecycle) {
                lifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    viewModel.isUserLoggedInChannel.collect { isLoggedIn ->
                        if (isLoggedIn) navigateToMainActivity()
                    }
                }
            }
            MuktimargaDarshiniTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    AnimatedContent(targetState = splashState, transitionSpec = {
                        fadeIn(animationSpec = tween(durationMillis = 1000)) togetherWith fadeOut(
                            animationSpec = tween(durationMillis = 1000)
                        )
                    }, label = "start") { splash ->
                        if (splash) {
                            SplashScreen()
                        } else {
                            AuthenticationNavigation()
                        }
                    }
                }

            }
        }
    }
}

@Composable
private fun AuthenticationNavigation() {
    val navController: NavHostController = rememberNavController()
    NavHost(
        modifier = Modifier.fillMaxSize(), navController = navController, startDestination = "login"
    ) {
        composable("login") {
            MobileAuthenticationPage(onRegisterClicked = {
                navController.navigate("register")
            })
        }
        composable("register") {
            RegisterPage(onSignInClick = {
                navController.navigateUp()
            })
        }
    }
}

private fun Activity.navigateToMainActivity() {
    startActivity(Intent(this, MainActivity::class.java))
    finish()
}