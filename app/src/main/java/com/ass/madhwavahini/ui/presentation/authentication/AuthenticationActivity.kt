package com.ass.madhwavahini.ui.presentation.authentication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
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
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ass.madhwavahini.data.Constants.SPLASH_TIMEOUT
import com.ass.madhwavahini.ui.presentation.MainActivity
import com.ass.madhwavahini.ui.presentation.SplashScreen
import com.ass.madhwavahini.ui.presentation.authentication.login.LoginPage
import com.ass.madhwavahini.ui.presentation.authentication.register.RegisterPage
import com.ass.madhwavahini.ui.theme.MuktimargaDarshiniTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlin.system.exitProcess

@AndroidEntryPoint
class AuthenticationActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            var backPressedTime by remember { mutableLongStateOf(0L) }
            BackHandler(onBack = {
                if (backPressedTime + 2000 > System.currentTimeMillis()) {
                    exitProcess(0)
                } else {
                    Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show()
                    backPressedTime = System.currentTimeMillis()
                }
            })

            var splashState by rememberSaveable {
                mutableStateOf(true)
            }
            LaunchedEffect(key1 = true) {
                delay(SPLASH_TIMEOUT)
                splashState = false
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
private fun Activity.AuthenticationNavigation() {
    val navController: NavHostController = rememberNavController()
    NavHost(
        modifier = Modifier.fillMaxSize(), navController = navController, startDestination = "login"
    ) {
        composable("login") {
            LoginPage(
                onRegisterClicked = {
                    navController.navigate("register")
                },
                onNavigate = { navigateToMainActivity() }
            )
        }
        composable("register") {
            RegisterPage(
                onSignInClick = {
                    navController.navigateUp()
                })
        }
    }
}

private fun Activity.navigateToMainActivity() {
    startActivity(Intent(this, MainActivity::class.java))
    finish()
}