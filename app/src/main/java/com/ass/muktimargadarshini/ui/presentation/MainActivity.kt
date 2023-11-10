package com.ass.muktimargadarshini.ui.presentation

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ass.muktimargadarshini.BuildConfig
import com.ass.muktimargadarshini.R
import com.ass.muktimargadarshini.domain.modals.Payment
import com.ass.muktimargadarshini.domain.modals.User
import com.ass.muktimargadarshini.ui.presentation.authentication.AuthenticationActivity
import com.ass.muktimargadarshini.ui.presentation.common.Loading
import com.ass.muktimargadarshini.ui.presentation.navigation.modal.NavigationFragment
import com.ass.muktimargadarshini.ui.theme.MuktimargaDarshiniTheme
import com.razorpay.Checkout
import com.razorpay.PaymentData
import com.razorpay.PaymentResultWithDataListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.json.JSONObject

@AndroidEntryPoint
class MainActivity : ComponentActivity(), PaymentResultWithDataListener {

    private val mainViewModel by viewModels<MainViewModel>()

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
        mainViewModel.paymentCancelled()
    }
}


@Composable
private fun Activity.MainPage(
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
        NavigationFragment.Pdf
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

    LaunchedEffect(key1 = lifeCycleOwner.lifecycle) {
        lifeCycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            mainViewModel.orderState.collectLatest { order ->
                order.data?.let { p ->
                    startPayment(p, user, mainViewModel)
                }
            }
        }
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
            AppBar(
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
    item: NavigationFragment, isSelected: Boolean, onMenuClick: (item: NavigationFragment) -> Unit
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    title: String,
    hamburgerIconClicked: () -> Unit,
    navigationBackClicked: () -> Unit,
    isNavigationFragment: Boolean,
    mainViewModel: MainViewModel,
    user: User
) {

    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ), title = {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }, navigationIcon = {
        if (isNavigationFragment) {
            Icon(imageVector = Icons.Filled.Menu,
                contentDescription = null,
                modifier = Modifier
                    .clickable { hamburgerIconClicked() }
                    .padding(8.dp))
        } else {
            Icon(imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .clickable { navigationBackClicked() }
                    .padding(8.dp))
        }
    }, actions = {
        if (!user.isPaidCustomer) IconButton(onClick = {
            mainViewModel.getOrder()
        }) {
            Text(text = "BUY")
        }

    })
}

fun Activity.startPayment(
    paymentData: Payment, user: User, mainViewModel: MainViewModel
) {
    val checkout = Checkout()
    checkout.setKeyID(BuildConfig.LIVE_KEY)
    checkout.setImage(R.mipmap.ic_launcher)
    try {
        val options = JSONObject()
        options.put("name", "Muktimarga Darshini")
        options.put("description", "Payment for Muktimarga darshini app")
        options.put("order_id", paymentData.id)
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


