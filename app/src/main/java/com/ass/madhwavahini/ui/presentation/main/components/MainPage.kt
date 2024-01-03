package com.ass.madhwavahini.ui.presentation.main.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ass.madhwavahini.R
import com.ass.madhwavahini.ui.presentation.main.MainActivity
import com.ass.madhwavahini.ui.presentation.main.MainViewModel
import com.ass.madhwavahini.ui.presentation.main.components.payment.PaymentBottomSheetModule
import com.ass.madhwavahini.ui.presentation.navigation.modal.rootNavigationFragmentsLists
import com.ass.madhwavahini.ui.theme.ScreenOrientation
import com.ass.madhwavahini.ui.theme.dimens

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun MainActivity.MainPage(
    mainViewModel: MainViewModel
) {
    val rootNavHostController: NavHostController = rememberNavController()
    val navBackStackEntry by rootNavHostController.currentBackStackEntryAsState()

    val snackBarHostState = remember { SnackbarHostState() }

    PaymentBottomSheetModule(
        snackBarHostState = snackBarHostState,
        orderState = mainViewModel.orderState,
        paymentState = mainViewModel.paymentState,
        user = mainViewModel.user,
        onError = mainViewModel::showError
    )

    // SIZE CALCULATION OF SCREEN
    val config: Configuration = LocalConfiguration.current
    val widthSizeClass: WindowWidthSizeClass = calculateWindowSizeClass(this).widthSizeClass
    val isLandscape = ScreenOrientation == Configuration.ORIENTATION_LANDSCAPE
    val isCompactDevice = WindowWidthSizeClass.Compact == widthSizeClass

    val navigationType = if (!isLandscape && isCompactDevice)
        NavigationType.BOTTOM_BAR
    else if (config.screenWidthDp >= 1000 && isLandscape)
        NavigationType.SIDE_DRAWER
    else NavigationType.SIDE_RAIL

    PermanentNavigationDrawer(drawerContent = {
        if (navigationType == NavigationType.SIDE_DRAWER)
            PermanentDrawerSheet(modifier = Modifier.padding(MaterialTheme.dimens.paddingLarge)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.app_logo),
                        contentDescription = null,
                        modifier = Modifier.size(80.dp)
                    )
                    Text(
                        text = stringResource(id = R.string.app_name),
                        style = MaterialTheme.typography.headlineSmall
                    )
                }
                rootNavigationFragmentsLists.forEach { item ->
                    val isSelected: Boolean = item.route == navBackStackEntry?.destination?.route
                    NavigationDrawerItem(selected = isSelected, onClick = {
                        rootNavHostController.navigate(item.route) {
                            popUpTo(rootNavHostController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }, label = {
                        Text(
                            text = item.title.asString(),
                            style = MaterialTheme.typography.labelMedium
                        )
                    }, icon = {
                        Icon(
                            imageVector = if (isSelected) item.selectedIcon else item.icon,
                            contentDescription = item.title.asString()
                        )
                    })
                }
            }

    }) {
        Row {
            if (navigationType == NavigationType.SIDE_RAIL)
                MyNavigationRail(shouldShowBuyButton = !mainViewModel.user.isPaidCustomer,
                    onBuyClick = mainViewModel::getOrder,
                    navBackStackEntry = navBackStackEntry,
                    onNavigate = {
                        rootNavHostController.navigate(it) {
                            popUpTo(rootNavHostController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    })

            MyScaffold(
                mainViewModel = mainViewModel,
                snackBarHostState = snackBarHostState,
                rootNavHostController = rootNavHostController,
                navBackStackEntry = navBackStackEntry,
                navigationType = navigationType
            )
        }
    }
}