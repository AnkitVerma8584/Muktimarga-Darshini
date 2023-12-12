package com.ass.madhwavahini.ui.presentation

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.ass.madhwavahini.BuildConfig
import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.modals.Payment
import com.ass.madhwavahini.ui.presentation.authentication.AuthenticationActivity
import com.ass.madhwavahini.ui.presentation.main_content.MainPage
import com.ass.madhwavahini.ui.presentation.main_content.dialog.NotificationPermissionRationalDialog
import com.ass.madhwavahini.ui.theme.MadhwaVahiniTheme
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.android.play.core.ktx.isImmediateUpdateAllowed
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import dagger.hilt.android.AndroidEntryPoint
import org.json.JSONObject

@AndroidEntryPoint
class MainActivity : ComponentActivity(), PaymentResultWithDataListener {

    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var appUpdateManager: AppUpdateManager
    private val updateType = AppUpdateType.IMMEDIATE
    private val updateLauncher =
        registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) { result: ActivityResult ->
            if (result.resultCode != RESULT_OK) {
                mainViewModel.showError("Update denied! Please update the app.")
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!BuildConfig.DEBUG)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE
            )
        appUpdateManager = AppUpdateManagerFactory.create(applicationContext)
        checkForAppUpdates()
        setContent {
            MadhwaVahiniTheme {
                val notificationPermissionResultLauncher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestPermission(),
                    onResult = { isGranted ->
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) mainViewModel.onPermissionResult(
                            isPermissionGranted = isGranted,
                            shouldShowPermissionRationalDialog = shouldShowRequestPermissionRationale(
                                Manifest.permission.POST_NOTIFICATIONS
                            )
                        )
                    })

                LaunchedEffect(key1 = Unit) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
                        notificationPermissionResultLauncher.launch(
                            Manifest.permission.POST_NOTIFICATIONS
                        )
                }

                if (mainViewModel.shouldShowPermissionRational) {
                    NotificationPermissionRationalDialog(onDismiss = mainViewModel::dismissDialog,
                        onOkClick = {
                            mainViewModel.dismissDialog()
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) notificationPermissionResultLauncher.launch(
                                Manifest.permission.POST_NOTIFICATIONS
                            )
                        })
                }

                if (mainViewModel.shouldLogOut) {
                    startActivity(Intent(this, AuthenticationActivity::class.java))
                    finish()
                }

                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    MainPage(mainViewModel)
                }
            }
        }
    }

    override fun onPaymentSuccess(p0: String?, p1: PaymentData?) {
        p1?.let { mainViewModel.verifyPayment(it) }
    }

    override fun onPaymentError(p0: Int, p1: String?, p2: PaymentData?) {
        mainViewModel.showError("Payment cancelled")
    }

    private fun checkForAppUpdates() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener { info ->
            val isUpdateAvailable = info.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE
            val isUpdateAllowed = info.isImmediateUpdateAllowed
            if (isUpdateAvailable && isUpdateAllowed) {
                appUpdateManager.startUpdateFlowForResult(
                    info,
                    updateLauncher,
                    AppUpdateOptions.newBuilder(updateType).build()
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                ) {
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        updateLauncher,
                        AppUpdateOptions.newBuilder(updateType).build()
                    )
                }
            }
    }
}

fun Activity.startPayment(
    paymentData: Payment, mainViewModel: MainViewModel
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
        options.put("prefill.email", "${mainViewModel.user.firstName}@gmail.com")
        options.put("prefill.contact", mainViewModel.user.userPhone)
        options.put("amount", paymentData.amount)
        val retryObj = JSONObject()
        retryObj.put("enabled", true)
        retryObj.put("max_count", 4)
        options.put("retry", retryObj)
        checkout.open(this, options)
    } catch (e: Exception) {
        mainViewModel.showError()
    }
}


