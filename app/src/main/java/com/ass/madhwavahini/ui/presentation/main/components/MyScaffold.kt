package com.ass.madhwavahini.ui.presentation.main.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.ass.madhwavahini.ui.presentation.common.Loading
import com.ass.madhwavahini.ui.presentation.common.MyCustomSnack
import com.ass.madhwavahini.ui.presentation.common.SnackBarType
import com.ass.madhwavahini.ui.presentation.main.MainViewModel
import com.ass.madhwavahini.ui.presentation.navigation.navhosts.BottomNavHostFragment
import kotlinx.coroutines.launch


@Composable
fun RowScope.MyScaffold(
    mainViewModel: MainViewModel,
    snackBarHostState: SnackbarHostState,
    rootNavHostController: NavHostController,
    navBackStackEntry: NavBackStackEntry?,
    shouldShowBottomBar: Boolean
) {
    val scope = rememberCoroutineScope()
    var bottomBarState by remember {
        mutableStateOf(true)
    }
    Scaffold(modifier = Modifier
        .fillMaxHeight()
        .weight(1f), snackbarHost = {
        SnackbarHost(hostState = snackBarHostState) { sb: SnackbarData ->
            MyCustomSnack(
                text = sb.visuals.message,
                snackBarType = SnackBarType.getType(sb.visuals.actionLabel)
            ) {
                snackBarHostState.currentSnackbarData?.dismiss()
            }
        }
    },
        floatingActionButton = {
            if (!mainViewModel.user.isPaidCustomer && shouldShowBottomBar)
                PurchasePackButton(onBuyClick = mainViewModel::getOrder)
        }, bottomBar = {
            if (bottomBarState && shouldShowBottomBar) {
                MyBottomNavigation(navBackStackEntry, onNavigate = {
                    rootNavHostController.navigate(it) {
                        popUpTo(rootNavHostController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
            }
        }) { padding ->

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
                user = mainViewModel.user,
                onLogout = mainViewModel::logout,
                onBottomBarStateChange = {
                    bottomBarState = it
                },
                onErrorTriggered = { message, type ->
                    scope.launch {
                        snackBarHostState.showSnackbar(
                            message = message,
                            duration = SnackbarDuration.Short,
                            actionLabel = type.name
                        )
                    }
                }
            )
        }
    }
}

@Composable
fun PurchasePackButton(
    onBuyClick: () -> Unit
) {
    FloatingActionButton(onClick = onBuyClick) {
        Text(text = "Buy")
    }
}