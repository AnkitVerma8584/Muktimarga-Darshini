package com.ass.madhwavahini.ui.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ass.madhwavahini.domain.modals.User
import com.ass.madhwavahini.ui.presentation.navigation.modal.HomeNavigationFragments
import com.ass.madhwavahini.ui.presentation.ui_new.aradhna.AradhnaPage
import com.ass.madhwavahini.ui.presentation.ui_new.home.HomePage
import com.ass.madhwavahini.ui.presentation.ui_new.panchanga.PanchangaPage

@Composable
fun HomeNavHostFragment(
    user: User,
    onRootNavigation: (route: String) -> Unit
) {
    val homeNavController = rememberNavController()

    NavHost(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
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