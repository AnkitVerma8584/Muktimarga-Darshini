package com.ass.muktimargadarshini.ui.presentation

import android.content.Context
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ass.madhwavahini.R
import com.ass.muktimargadarshini.ui.presentation.navigation.modal.NavigationFragment
import com.ass.muktimargadarshini.ui.theme.MuktimargaDarshiniTheme
import com.ass.muktimargadarshini.util.locale.LocaleHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE
        )
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            MuktimargaDarshiniTheme {
                var splashState by rememberSaveable {
                    mutableStateOf(true)
                }
                LaunchedEffect(key1 = true) {
                    delay(2000)
                    splashState = false
                }
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    AnimatedContent(targetState = splashState, transitionSpec = {
                        fadeIn(animationSpec = tween(durationMillis = 1000)) togetherWith fadeOut(
                            animationSpec = tween(durationMillis = 1000)
                        )
                    }, label = "start") { splash ->
                        if (splash) {
                            SplashScreen()
                        } else MainPage(windowSizeClass)
                    }
                }
            }
        }
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase!!))
    }
}


@Composable
private fun MainPage(
    windowSizeClass: WindowSizeClass, allScreens: List<NavigationFragment> = listOf(
        NavigationFragment.Home,
        NavigationFragment.About,
        NavigationFragment.Contact,
        NavigationFragment.Support,
        NavigationFragment.SubCategory,
        NavigationFragment.Files,
        NavigationFragment.SubToSubCategory,
        NavigationFragment.FileDetails,
        NavigationFragment.Pdf
    ), menuScreens: List<NavigationFragment> = listOf(
        NavigationFragment.Home,
        NavigationFragment.About,
        NavigationFragment.Contact,
        NavigationFragment.Support
    )
) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    val currentFragment by remember(currentDestination) {
        derivedStateOf {
            allScreens.find { it.route == navController.currentBackStackEntry?.destination?.route }
        }
    }
    ModalNavigationDrawer(drawerState = drawerState,
        gesturesEnabled = currentFragment?.icon != null,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(Modifier.height(24.dp))
                androidx.compose.material.Text(
                    text = stringResource(id = R.string.menu),
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
                Spacer(Modifier.height(8.dp))
                menuScreens.forEach { item ->
                    MenuItem(item = item,
                        isSelected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onMenuClick = {
                            scope.launch { drawerState.close() }
                            navController.navigate(it.route) {
                                popUpTo(navController.graph.findStartDestination().id)
                                launchSingleTop = true
                            }
                        })
                }
            }
        }) {
        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            AppBar(
                title = currentFragment?.title?.asString()
                    ?: stringResource(id = R.string.app_name),
                hamburgerIconClicked = { scope.launch { drawerState.open() } },
                navigationBackClicked = { navController.navigateUp() },
                isNavigationFragment = currentFragment?.icon != null
            )
        }) {
            NavHostFragments(
                navController = navController, paddingValues = it, windowSizeClass = windowSizeClass
            )
        }
    }
}

@Composable
private fun MenuItem(
    item: NavigationFragment, isSelected: Boolean, onMenuClick: (item: NavigationFragment) -> Unit
) {
    NavigationDrawerItem(icon = {
        item.icon?.let {
            Image(
                painter = painterResource(id = it),
                contentDescription = null,
                colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onPrimaryContainer)
            )
        }
    }, label = {
        androidx.compose.material.Text(
            item.title.asString(),
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.labelLarge
        )
    }, selected = isSelected, onClick = {
        onMenuClick(item)
    }, modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AppBar(
    title: String,
    hamburgerIconClicked: () -> Unit,
    navigationBackClicked: () -> Unit,
    isNavigationFragment: Boolean
) {
    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        navigationIconContentColor = MaterialTheme.colorScheme.onPrimaryContainer
    ), title = {
        androidx.compose.material.Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }, navigationIcon = {
        if (isNavigationFragment) {
            Icon(imageVector = Icons.Filled.Menu,
                contentDescription = null,
                modifier = Modifier
                    .clickable { hamburgerIconClicked() }
                    .padding(8.dp))
        } else {
            Icon(imageVector = Icons.Filled.ArrowBack,
                contentDescription = null,
                modifier = Modifier
                    .clickable { navigationBackClicked() }
                    .padding(8.dp))
        }
    }, actions = {
        TopAppBarDropdownMenu()
    })
}

@Composable
private fun TopAppBarDropdownMenu() {
    val expanded = remember { mutableStateOf(false) }
    Box(
        Modifier.wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = {
            expanded.value = true
        }) {
            Icon(
                painterResource(id = R.drawable.ic_language), contentDescription = "Change Language"
            )
        }
    }
    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false },
    ) {
        MenuItem(R.string.en)
        MenuItem(R.string.hi)
        MenuItem(R.string.kn)
        MenuItem(R.string.sa)
    }
}

@Composable
private fun MenuItem(
    @StringRes languageId: Int
) {
    DropdownMenuItem(text = {
        androidx.compose.material.Text(
            text = stringResource(id = languageId),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer
        )
    }, onClick = { })
}
