package com.ass.madhwavahini.ui.presentation

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import com.ass.madhwavahini.BuildConfig
import com.ass.madhwavahini.R
import com.ass.madhwavahini.data.Constants
import com.ass.madhwavahini.domain.modals.Payment
import com.ass.madhwavahini.domain.modals.User
import com.ass.madhwavahini.ui.presentation.main_content.MainPage
import com.ass.madhwavahini.ui.theme.MuktimargaDarshiniTheme
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.ktx.messaging
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class MainActivity : ComponentActivity(), PaymentResultWithDataListener {

    private val mainViewModel by viewModels<MainViewModel>()

    // Declare the launcher at the top of your Activity/Fragment:
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { isGranted: Boolean ->
        if (isGranted) {
            // FCM SDK (and your app) can post notifications.
        } else {
            // TODO: Inform user that that your app will not show notifications.
        }
    }

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE
        )
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            MuktimargaDarshiniTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainPage(windowSizeClass, mainViewModel)
                }
            }
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        p1?.let { mainViewModel.verifyPayment(it) }
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        mainViewModel.errorInPayment("Payment cancelled")
    }


    @Composable
    private fun AskNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) ==
                PackageManager.PERMISSION_GRANTED
            ) {
                Firebase.messaging.subscribeToTopic(Constants.TOPIC)
            } else if (shouldShowRequestPermissionRationale(Manifest.permission.POST_NOTIFICATIONS)) {
                val dialog = AlertDialog(
                    onDismissRequest = { /*TODO*/ },
                    confirmButton = { /*TODO*/ })

                // TODO: display an educational UI explaining to the user the features that will be enabled
                //       by them granting the POST_NOTIFICATION permission. This UI should provide the user
                //       "OK" and "No thanks" buttons. If the user selects "OK," directly request the permission.
                //       If the user selects "No thanks," allow the user to continue without notifications.
            } else {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }
}


fun Activity.startPayment(
    paymentData: Payment, user: User, mainViewModel: MainViewModel
) {
    val checkout = Checkout()
    checkout.setKeyID(BuildConfig.LIVE_KEY)
    checkout.setImage(R.mipmap.ic_launcher)
    try {
        val options = JSONObject()
        options.put("name", "Madhva Vahini")
        options.put("description", "Payment for Madhva Vahini app")
        options.put("order_id", paymentData.orderId)
        options.put("theme.color", "#934B00")
        options.put("currency", "INR")
        options.put("prefill.email", "${user.userName}@gmail.com")
        options.put("prefill.contact", user.userPhone)
        options.put("amount", paymentData.amount)
        val retryObj = JSONObject()
        retryObj.put("enabled", true)
        retryObj.put("max_count", 4)
        options.put("retry", retryObj)
        checkout.open(this, options)
    } catch (e: Exception) {
        mainViewModel.errorInPayment()
    }
}


