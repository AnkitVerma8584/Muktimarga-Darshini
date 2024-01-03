package com.ass.madhwavahini.ui.presentation.navigation.modal

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.wrapper.StringUtil

sealed class RootNavigationFragments(
    val title: StringUtil,
    val route: String,
    val icon: ImageVector,
    val selectedIcon: ImageVector
) {
    data object Home : RootNavigationFragments(
        route = "home",
        title = StringUtil.StringResource(R.string.home),
        icon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home
    )

    data object Category : RootNavigationFragments(
        route = "category_home",
        title = StringUtil.StringResource(R.string.category),
        icon = Icons.Outlined.Category,
        selectedIcon = Icons.Filled.Category
    )

    data object Gallery : RootNavigationFragments(
        route = "gallery",
        title = StringUtil.StringResource(R.string.gallery),
        icon = Icons.Outlined.Camera,
        selectedIcon = Icons.Filled.Camera
    )

    data object Setting : RootNavigationFragments(
        route = "setting_home",
        title = StringUtil.StringResource(R.string.settings),
        icon = Icons.Outlined.Settings,
        selectedIcon = Icons.Filled.Settings
    )
}

val rootNavigationFragmentsLists = listOf(
    RootNavigationFragments.Home,
    RootNavigationFragments.Category,
    //RootNavigationFragments.Gallery,
    RootNavigationFragments.Setting
)