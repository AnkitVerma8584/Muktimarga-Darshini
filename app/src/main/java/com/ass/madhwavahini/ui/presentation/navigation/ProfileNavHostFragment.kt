package com.ass.madhwavahini.ui.presentation.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ass.madhwavahini.domain.modals.User
import com.ass.madhwavahini.ui.presentation.navigation.modal.ProfileNavigationFragments
import com.ass.madhwavahini.ui.presentation.navigation.screens.about.AboutPage
import com.ass.madhwavahini.ui.presentation.navigation.screens.contact.ContactPage
import com.ass.madhwavahini.ui.presentation.navigation.screens.support.SupportPage
import com.ass.madhwavahini.ui.presentation.ui_new.profile.ProfilePage

@Composable
fun ProfileNavHostFragment(
    user:User,
    onLogOut: () -> Unit
) {
    val profileNavController = rememberNavController()
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = profileNavController,
        startDestination = ProfileNavigationFragments.Profile.route
    ) {

        composable(route = ProfileNavigationFragments.Profile.route) {
            ProfilePage(user=user,onLogout = onLogOut) { route ->
                profileNavController.navigate(route)
            }
        }

        composable(route = ProfileNavigationFragments.AboutUs.route) {
            AboutPage()
        }

        composable(route = ProfileNavigationFragments.ContactUs.route) {
            ContactPage()
        }
        composable(route = ProfileNavigationFragments.Support.route) {
            SupportPage()
        }

    }
}