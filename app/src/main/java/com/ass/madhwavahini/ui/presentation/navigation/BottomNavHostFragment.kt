package com.ass.madhwavahini.ui.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ass.madhwavahini.ui.presentation.common.SnackBarType
import com.ass.madhwavahini.ui.presentation.navigation.modal.BottomNavigationFragments
import com.ass.madhwavahini.ui.presentation.navigation.modal.bottomNavigationFragmentsList
import com.ass.madhwavahini.ui_new.aradhna.AradhnaPage
import com.ass.madhwavahini.ui_new.home.HomePage
import com.ass.madhwavahini.ui_new.profile.ProfilePage

@Composable
fun BottomNavHostFragment(
    navController: NavHostController,
    isPaidCustomer: Boolean,
    onErrorTriggered: (message: String, type: SnackBarType) -> Unit
) {

    fun AnimatedContentTransitionScope<NavBackStackEntry>.getInAnimation(): EnterTransition =
        slideInHorizontally(animationSpec = tween(700)) {
            val initial =
                bottomNavigationFragmentsList.indexOf(bottomNavigationFragmentsList.find { item -> item.route == initialState.destination.route })
            val target =
                bottomNavigationFragmentsList.indexOf(bottomNavigationFragmentsList.find { item -> item.route == targetState.destination.route })
            if (initial < target) it else -it
        }


    fun AnimatedContentTransitionScope<NavBackStackEntry>.getOutAnimation(): ExitTransition =
        slideOutHorizontally(animationSpec = tween(700)) {
            val initial =
                bottomNavigationFragmentsList.indexOf(bottomNavigationFragmentsList.find { item -> item.route == initialState.destination.route })
            val target =
                bottomNavigationFragmentsList.indexOf(bottomNavigationFragmentsList.find { item -> item.route == targetState.destination.route })
            if (initial < target) -it else it
        }

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = BottomNavigationFragments.Home.route,
        enterTransition = { getInAnimation() },
        exitTransition = {
            getOutAnimation()
        },
        popEnterTransition = { getInAnimation() },
        popExitTransition = { getOutAnimation() },
    ) {
        composable(route = BottomNavigationFragments.Home.route) {
            HomePage { route ->
                navController.navigate(route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }

        composable(route = BottomNavigationFragments.Category.route) {
            CategoryNavHostFragment(
                isPaidCustomer = isPaidCustomer, onErrorTriggered = onErrorTriggered
            )
        }
        composable(route = BottomNavigationFragments.Aradhna.route) {
            AradhnaPage()
        }
        composable(route = BottomNavigationFragments.Profile.route) {
            ProfilePage()
        }
    }
}
