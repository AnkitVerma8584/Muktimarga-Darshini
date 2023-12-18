package com.ass.madhwavahini.ui.presentation.navigation.navhosts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.modals.User
import com.ass.madhwavahini.ui.presentation.main.components.MyTopAppBar
import com.ass.madhwavahini.ui.presentation.navigation.destinations.home.HomePage
import com.ass.madhwavahini.ui.presentation.navigation.destinations.home.aradhna.AradhnaPage
import com.ass.madhwavahini.ui.presentation.navigation.destinations.home.panchanga.PanchangaPage
import com.ass.madhwavahini.ui.presentation.navigation.modal.HomeNavigationFragments
import com.ass.madhwavahini.ui.presentation.navigation.modal.homeNavigationFragmentsList

@Composable
fun HomeNavHostFragment(
    user: User,
    onRootNavigation: (route: String) -> Unit
) {
    val homeNavController = rememberNavController()

    val navBackStackEntry by homeNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val currentFragment by remember(currentDestination) {
        derivedStateOf {
            homeNavigationFragmentsList.find { it.route == currentDestination?.route }
        }
    }

    Scaffold(topBar = {
        if (currentFragment?.route in arrayOf(
                HomeNavigationFragments.Panchanga.route,
                HomeNavigationFragments.Aradhna.route
            )
        )
            MyTopAppBar(
                title = currentFragment?.title?.asString()
                    ?: stringResource(id = R.string.home),
                onNavigate = homeNavController::navigateUp
            )
    }) { padding ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            navController = homeNavController,
            startDestination = HomeNavigationFragments.Home.route
        ) {
            composable(route = HomeNavigationFragments.Home.route) {
                HomePage(user = user, onRootNavigation = onRootNavigation) { route ->
                    homeNavController.navigate(route) {
                        popUpTo(homeNavController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            }
            composable(route = HomeNavigationFragments.Aradhna.route) {
                AradhnaPage()
            }
            composable(route = HomeNavigationFragments.Panchanga.route) {
                PanchangaPage()
            }
        }
    }
}