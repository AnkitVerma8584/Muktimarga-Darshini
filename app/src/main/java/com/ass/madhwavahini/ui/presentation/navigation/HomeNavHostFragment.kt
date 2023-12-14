package com.ass.madhwavahini.ui.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ass.madhwavahini.ui.presentation.navigation.modal.HomeNavigationFragments
import com.ass.madhwavahini.ui.presentation.ui_new.aradhna.AradhnaPage
import com.ass.madhwavahini.ui.presentation.ui_new.home.HomePage
import com.ass.madhwavahini.ui.presentation.ui_new.panchanga.PanchangaPage

@Composable
fun HomeNavHostFragment(
    onRootNavigation: (route: String) -> Unit
) {
    val homeNavController = rememberNavController()
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = homeNavController,
        startDestination = HomeNavigationFragments.Home.route
    ) {
        composable(route = HomeNavigationFragments.Home.route) {
            HomePage(onRootNavigation = onRootNavigation) { route ->
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