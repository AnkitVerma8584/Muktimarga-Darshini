package com.ass.madhwavahini.ui.presentation.navigation.navhosts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.modals.User
import com.ass.madhwavahini.ui.presentation.navigation.modal.SettingsNavHostFragments
import com.ass.madhwavahini.ui.presentation.navigation.modal.settingsFragmentList
import com.ass.madhwavahini.ui.presentation.navigation.destinations.settings.about.AboutPage
import com.ass.madhwavahini.ui.presentation.navigation.destinations.settings.contact.ContactPage
import com.ass.madhwavahini.ui.presentation.navigation.destinations.settings.support.SupportPage
import com.ass.madhwavahini.ui.presentation.navigation.destinations.settings.SettingsPage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsNavHostFragment(
    user: User,
    onNavigateBack: () -> Unit,
    onLogOut: () -> Unit
) {
    val settingsNavController = rememberNavController()

    val navBackStackEntry by settingsNavController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val currentFragment by remember(currentDestination) {
        derivedStateOf {
            settingsFragmentList.find { it.route == currentDestination?.route }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.Transparent,
                navigationIconContentColor = MaterialTheme.colorScheme.onBackground
            ), title = {
                Text(
                    text = currentFragment?.title?.asString()
                        ?: stringResource(id = R.string.settings),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Medium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }, navigationIcon = {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Navigate Back",
                    modifier = Modifier
                        .clickable {
                            if (SettingsNavHostFragments.Settings.route == currentFragment?.route)
                                onNavigateBack.invoke()
                            else
                                settingsNavController.navigateUp()
                        }
                        .padding(8.dp)
                )
            })
        }
    ) { padding ->
        NavHost(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            navController = settingsNavController,
            startDestination = SettingsNavHostFragments.Settings.route
        ) {

            composable(route = SettingsNavHostFragments.Settings.route) {
                SettingsPage(
                    user = user,
                    onLogout = onLogOut,
                    onNavigate = settingsNavController::navigate
                )
            }

            composable(route = SettingsNavHostFragments.AboutUs.route) {
                AboutPage()
            }

            composable(route = SettingsNavHostFragments.ContactUs.route) {
                ContactPage()
            }
            composable(route = SettingsNavHostFragments.Support.route) {
                SupportPage()
            }

        }
    }
}