package com.ass.madhwavahini.ui.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ass.madhwavahini.ui.presentation.navigation.modal.BottomNavigationFragments
import com.ass.madhwavahini.ui.presentation.navigation.modal.CategoryNavigationFragments
import com.ass.madhwavahini.ui.presentation.navigation.screens.category.CategoryPage
import com.ass.madhwavahini.ui.presentation.ui_new.gallery.GalleryPage
import com.ass.madhwavahini.ui.presentation.ui_new.profile.ProfilePage

@Composable
fun ProfileNavHostFragment() {
    val profileNavController = rememberNavController()
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = profileNavController,
        startDestination = BottomNavigationFragments.Home.route
    ) {

        composable(route = BottomNavigationFragments.Home.route) {
           /* HomePage { route ->
                profileNavController.navigate(route) {
                    popUpTo(profileNavController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }*/
        }

        composable(route = CategoryNavigationFragments.Category.route) {
            CategoryPage()
        }
        composable(route = BottomNavigationFragments.Gallery.route) {
            GalleryPage()
        }
        composable(route = BottomNavigationFragments.Profile.route) {
            //ProfilePage()
        }
    }
}