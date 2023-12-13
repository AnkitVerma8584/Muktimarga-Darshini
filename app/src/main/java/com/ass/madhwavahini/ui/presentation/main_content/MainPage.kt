package com.ass.madhwavahini.ui.presentation.main_content

import android.app.Activity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ass.madhwavahini.domain.modals.Payment
import com.ass.madhwavahini.ui.presentation.MainViewModel
import com.ass.madhwavahini.ui.presentation.common.Loading
import com.ass.madhwavahini.ui.presentation.common.MyCustomSnack
import com.ass.madhwavahini.ui.presentation.common.SnackBarType
import com.ass.madhwavahini.ui.presentation.navigation.BottomNavHostFragment
import com.ass.madhwavahini.ui.presentation.payment.PaymentOptionsBottomSheet
import com.ass.madhwavahini.ui.presentation.startPayment
import com.ass.madhwavahini.ui_new.home.MyBottomNavigation
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Activity.MainPage(
    mainViewModel: MainViewModel
) {
    val scope = rememberCoroutineScope()
    val rootNavHostController: NavHostController = rememberNavController()
    val navBackStackEntry by rootNavHostController.currentBackStackEntryAsState()
    val snackBarHostState = remember { SnackbarHostState() }
    val lifeCycleOwner = LocalLifecycleOwner.current

    var paymentData by remember {
        mutableStateOf<Payment?>(null)
    }
    LaunchedEffect(key1 = lifeCycleOwner.lifecycle) {
        lifeCycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            mainViewModel.orderState.collectLatest { order ->
                paymentData = order.data
            }
        }
    }

    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    paymentData?.let { payment ->
        PaymentOptionsBottomSheet(sheetState = sheetState,
            paymentData = payment,
            onDismiss = { paymentData = null },
            onRazorpayModeClicked = {
                startPayment(payment, mainViewModel)
                paymentData = null
            })
    }

    Scaffold(modifier = Modifier.fillMaxSize(), snackbarHost = {
        SnackbarHost(hostState = snackBarHostState) { sb: SnackbarData ->
            MyCustomSnack(
                text = sb.visuals.message,
                snackBarType = SnackBarType.getType(sb.visuals.actionLabel)
            ) {
                snackBarHostState.currentSnackbarData?.dismiss()
            }
        }
    }, bottomBar = {
        MyBottomNavigation(navBackStackEntry, onNavigate = {
            rootNavHostController.navigate(it) {
                popUpTo(rootNavHostController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        })
    }) { padding ->
        val ctx = LocalContext.current
        LaunchedEffect(key1 = lifeCycleOwner.lifecycle) {
            lifeCycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                mainViewModel.paymentState.collectLatest { payment ->
                    payment.data?.let {
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                message = "Payment Verified.",
                                duration = SnackbarDuration.Short
                            )
                        }
                    }
                    payment.error?.let { txt ->
                        scope.launch {
                            snackBarHostState.showSnackbar(
                                message = txt.asString(context = ctx),
                                duration = SnackbarDuration.Short,
                                actionLabel = SnackBarType.ERROR.name
                            )
                        }
                    }
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            if (mainViewModel.isLoading) {
                Loading()
            }
            BottomNavHostFragment(
                navController = rootNavHostController,
                isPaidCustomer = mainViewModel.user.isPaidCustomer,
                onErrorTriggered = { message, type ->
                    scope.launch {
                        snackBarHostState.showSnackbar(
                            message = message,
                            duration = SnackbarDuration.Short,
                            actionLabel = type.name
                        )
                    }
                })
        }
    }

}

