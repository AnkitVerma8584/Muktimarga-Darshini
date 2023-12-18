package com.ass.madhwavahini.ui.presentation.navigation.navhosts

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
import com.ass.madhwavahini.domain.modals.User
import com.ass.madhwavahini.ui.presentation.common.SnackBarType
import com.ass.madhwavahini.ui.presentation.navigation.destinations.gallery.GalleryPage
import com.ass.madhwavahini.ui.presentation.navigation.modal.RootNavigationFragments
import com.ass.madhwavahini.ui.presentation.navigation.modal.rootNavigationFragmentsLists

@Composable
fun BottomNavHostFragment(
    navController: NavHostController,
    user: User,
    onLogout: () -> Unit,
    onBottomBarStateChange: (state: Boolean) -> Unit,
    onErrorTriggered: (message: String, type: SnackBarType) -> Unit
) {

    fun AnimatedContentTransitionScope<NavBackStackEntry>.getInAnimation(): EnterTransition =
        slideInHorizontally(animationSpec = tween(500)) {
            val initial =
                rootNavigationFragmentsLists.indexOf(rootNavigationFragmentsLists.find { item -> item.route == initialState.destination.route })
            val target =
                rootNavigationFragmentsLists.indexOf(rootNavigationFragmentsLists.find { item -> item.route == targetState.destination.route })
            if (initial < target) it else -it
        }

    fun AnimatedContentTransitionScope<NavBackStackEntry>.getOutAnimation(): ExitTransition =
        slideOutHorizontally(animationSpec = tween(500)) {
            val initial =
                rootNavigationFragmentsLists.indexOf(rootNavigationFragmentsLists.find { item -> item.route == initialState.destination.route })
            val target =
                rootNavigationFragmentsLists.indexOf(rootNavigationFragmentsLists.find { item -> item.route == targetState.destination.route })
            if (initial < target) -it else it
        }

    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = RootNavigationFragments.Home.route,
        enterTransition = { getInAnimation() },
        exitTransition = {
            getOutAnimation()
        },
        popEnterTransition = { getInAnimation() },
        popExitTransition = { getOutAnimation() },
    ) {
        composable(route = RootNavigationFragments.Home.route) {
            HomeNavHostFragment(user = user) { route ->
                navController.navigate(route) {
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
        composable(route = RootNavigationFragments.Category.route) {
            CategoryNavHostFragment(
                user = user,
                onErrorTriggered = onErrorTriggered,
                onBottomBarStateChange = onBottomBarStateChange,
                onNavigateBack = navController::navigateUp
            )
        }
        composable(route = RootNavigationFragments.Gallery.route) {
            GalleryPage()
        }
        composable(route = RootNavigationFragments.Setting.route) {
            SettingsNavHostFragment(
                user = user, onNavigateBack = navController::navigateUp, onLogOut = onLogout
            )
        }
    }

}
