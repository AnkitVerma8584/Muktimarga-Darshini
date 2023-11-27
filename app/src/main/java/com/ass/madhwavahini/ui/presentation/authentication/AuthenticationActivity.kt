package com.ass.madhwavahini.ui.presentation.authentication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.compose.animation.core.SpringSpec
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.ass.madhwavahini.R
import com.ass.madhwavahini.data.Constants.SPLASH_TIMEOUT
import com.ass.madhwavahini.ui.presentation.MainActivity
import com.ass.madhwavahini.ui.presentation.SplashScreen
import com.ass.madhwavahini.ui.presentation.authentication.login.LoginPage
import com.ass.madhwavahini.ui.presentation.authentication.password.ResetPasswordFragment
import com.ass.madhwavahini.ui.presentation.authentication.register.RegisterPage
import com.ass.madhwavahini.ui.theme.MadhwaVahiniTheme
import com.skydoves.orbital.Orbital
import com.skydoves.orbital.animateSharedElementTransition
import com.skydoves.orbital.rememberContentWithOrbitalScope
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
            MadhwaVahiniTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    val sharedImage = rememberContentWithOrbitalScope {
                        Image(
                            modifier = Modifier
                                .size(180.dp)
                                .animateSharedElementTransition(
                                    orbitalScope = this,
                                    movementSpec = SpringSpec(stiffness = 200f),
                                    transformSpec = SpringSpec(stiffness = 200f)
                                ),
                            painter = rememberAsyncImagePainter(model = R.drawable.app_logo),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                        )
                    }
                    var splashState by rememberSaveable {
                        mutableStateOf(true)
                    }
                    LaunchedEffect(key1 = true) {
                        delay(SPLASH_TIMEOUT)
                        splashState = false
                    }

                    Orbital {
                        if (splashState) {
                            SplashScreen { sharedImage() }
                        } else {
                            AuthenticationNavigation { sharedImage() }
                        }
                    }
                }
            }
        }
    }
}


@Composable
private fun Activity.AuthenticationNavigation(
    sharedImage: @Composable () -> Unit
) {
    val navController: NavHostController = rememberNavController()
    NavHost(
        modifier = Modifier.fillMaxSize(), navController = navController, startDestination = "login"
    ) {
        composable("login") {
            LoginPage(
                sharedImage = sharedImage,
                onRegisterClicked = {
                    navController.navigate("register")
                },
                onNavigate = { navigateToMainActivity() },
                onForgetPassword = { navController.navigate("mobile_auth") }
            )
        }
        composable("register") {
            RegisterPage(sharedImage = sharedImage,
                onSignInClick = {
                    navController.navigateUp()
                }, onNavigate = { navigateToMainActivity() })
        }
        composable("mobile_auth") {
            ResetPasswordFragment(
                sharedImage = sharedImage,
                onPasswordResetCompleted = navController::navigateUp
            )
        }
    }
}

private fun Activity.navigateToMainActivity() {
    startActivity(Intent(this, MainActivity::class.java))
    finish()
}
