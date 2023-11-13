package com.ass.madhwavahini.ui.presentation.main_content

import android.app.Activity
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.modals.Payment
import com.ass.madhwavahini.ui.presentation.MainViewModel
import com.ass.madhwavahini.ui.presentation.authentication.AuthenticationActivity
import com.ass.madhwavahini.ui.presentation.common.Loading
import com.ass.madhwavahini.ui.presentation.navigation.NavHostFragments
import com.ass.madhwavahini.ui.presentation.navigation.modal.NavigationFragment
import com.ass.madhwavahini.ui.presentation.payment.PaymentOptionsBottomSheet
import com.ass.madhwavahini.ui.presentation.startPayment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Activity.MainPage(
    windowSizeClass: WindowSizeClass, mainViewModel: MainViewModel
) {
    val allScreens: List<NavigationFragment> = listOf(
        NavigationFragment.Home,
        NavigationFragment.About,
        NavigationFragment.Contact,
        NavigationFragment.Support,
        NavigationFragment.SubCategory,
        NavigationFragment.Files,
        NavigationFragment.SubToSubCategory,
        NavigationFragment.FileDetails,
        NavigationFragment.Pdf,
        NavigationFragment.Music
    )
    val menuScreens: List<NavigationFragment> = listOf(
        NavigationFragment.Home,
        NavigationFragment.About,
        NavigationFragment.Contact,
        NavigationFragment.Support
    )
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val currentDestination = navBackStackEntry?.destination

    val currentFragment by remember(currentDestination) {
        derivedStateOf {
            allScreens.find { it.route == navController.currentBackStackEntry?.destination?.route }
        }
    }
    val snackbarHostState = remember { SnackbarHostState() }

    val user by mainViewModel.user.collectAsStateWithLifecycle()

    val lifeCycleOwner = LocalLifecycleOwner.current

    var paymentData by remember {
        mutableStateOf<Payment?>(null)
    }
    LaunchedEffect(key1 = lifeCycleOwner.lifecycle) {
        lifeCycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            mainViewModel.orderState.collectLatest { order ->
                paymentData = order.data
                /*
                    order.data?.let { p ->
                        startPayment(p, user, mainViewModel)
                    }*/
            }
        }
    }

    val sheetState = rememberModalBottomSheetState()

    paymentData?.let { payment ->
        PaymentOptionsBottomSheet(
            sheetState = sheetState,
            paymentData = payment,
            onDismiss = { paymentData = null },
            onRazorpayModeClicked = {
                startPayment(payment, user, mainViewModel)
                paymentData = null
            },
            onUpiSubmitClicked = { tid, amount ->

            }
        )
    }


    ModalNavigationDrawer(drawerState = drawerState,
        gesturesEnabled = currentFragment?.icon != null,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .fillMaxHeight()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(Modifier.height(24.dp))
                Text(
                    text = "Hello ${user.userName},",
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                Text(
                    text = user.userPhone,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                Spacer(Modifier.height(24.dp))
                Text(
                    text = stringResource(id = R.string.menu),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                Spacer(Modifier.height(8.dp))
                menuScreens.forEach { item ->
                    MenuItem(item = item,
                        isSelected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onMenuClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(it.route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        })
                }
                NavigationDrawerItem(icon = {
                    Icon(
                        imageVector = Icons.Filled.Logout,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }, label = {
                    Text(
                        text = stringResource(id = R.string.logout),
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        style = MaterialTheme.typography.labelLarge
                    )
                }, selected = false, onClick = {
                    mainViewModel.logout()
                    startActivity(Intent(this@MainPage, AuthenticationActivity::class.java))
                    finish()
                }, modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }) {
        Scaffold(modifier = Modifier.fillMaxSize(), snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }, topBar = {
            MyAppBar(
                title = currentFragment?.title?.asString()
                    ?: stringResource(id = R.string.app_name),
                hamburgerIconClicked = { scope.launch { drawerState.open() } },
                navigationBackClicked = { navController.navigateUp() },
                isNavigationFragment = currentFragment?.icon != null,
                mainViewModel = mainViewModel,
                user = user
            )
        }) {
            val ctx = LocalContext.current
            LaunchedEffect(key1 = lifeCycleOwner.lifecycle) {
                lifeCycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                    mainViewModel.paymentState.collectLatest { payment ->
                        payment.data?.let {
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = "Payment verified", duration = SnackbarDuration.Short
                                )
                            }
                        }
                        payment.error?.let { txt ->
                            scope.launch {
                                snackbarHostState.showSnackbar(
                                    message = txt.asString(context = ctx),
                                    duration = SnackbarDuration.Short
                                )
                            }
                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it), contentAlignment = Alignment.Center
            ) {
                if (mainViewModel.isLoading.value) {
                    Loading()
                }
                NavHostFragments(navController = navController,
                    windowSizeClass = windowSizeClass,
                    isPaidCustomer = user.isPaidCustomer,
                    onNavigationTriggered = {
                        scope.launch {
                            snackbarHostState.showSnackbar(
                                message = "Purchase the pack to use.",
                                duration = SnackbarDuration.Short
                            )
                        }
                    })
            }
        }
    }
}


@Composable
private fun MenuItem(
    item: NavigationFragment,
    isSelected: Boolean,
    onMenuClick: (item: NavigationFragment) -> Unit
) {
    NavigationDrawerItem(icon = {
        item.icon?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimaryContainer)
            )
        }
    }, label = {
        Text(
            item.title.asString(),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.labelLarge
        )
    }, selected = isSelected, onClick = {
        onMenuClick(item)
    }, modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}

