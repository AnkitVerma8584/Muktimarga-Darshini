package com.ass.madhwavahini.ui.presentation.navigation.navhosts

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ass.madhwavahini.domain.modals.User
import com.ass.madhwavahini.ui.presentation.navigation.destinations.home.HomePage
import com.ass.madhwavahini.ui.presentation.navigation.destinations.home.aradhna.AradhnaPage
import com.ass.madhwavahini.ui.presentation.navigation.destinations.home.panchanga.PanchangaPage
import com.ass.madhwavahini.ui.presentation.navigation.modal.HomeNavigationFragments

@Composable
fun HomeNavHostFragment(
    user: User,
    onRootNavigation: (route: String) -> Unit
) {
    val homeNavController = rememberNavController()

    NavHost(
        modifier = Modifier.fillMaxSize(),
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
            AradhnaPage(onNavigateBack = homeNavController::navigateUp)
        }
        composable(route = HomeNavigationFragments.Panchanga.route) {
            PanchangaPage()
        }
    }
}