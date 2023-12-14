package com.ass.madhwavahini.ui.presentation.navigation.modal

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Camera
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.wrapper.StringUtil

sealed class BottomNavigationFragments(
    val title: StringUtil,
    val route: String,
    val icon: ImageVector,
    val selectedIcon:ImageVector
) {
    data object Home : BottomNavigationFragments(
        route = "home",
        title = StringUtil.StringResource(R.string.home),
        icon = Icons.Outlined.Home,
        selectedIcon = Icons.Filled.Home
    )

    data object Category : BottomNavigationFragments(
        route = "category_home",
        title = StringUtil.StringResource(R.string.category),
        icon = Icons.Outlined.Category,
        selectedIcon = Icons.Filled.Category
    )

    data object Gallery : BottomNavigationFragments(
        route = "gallery",
        title = StringUtil.StringResource(R.string.gallery),
        icon = Icons.Outlined.Camera,
        selectedIcon = Icons.Filled.Camera
    )

    data object Profile : BottomNavigationFragments(
        route = "profile_home",
        title = StringUtil.StringResource(R.string.profile),
        icon = Icons.Outlined.Person,
        selectedIcon = Icons.Filled.Person
    )
}

val bottomNavigationFragmentsList = listOf(
    BottomNavigationFragments.Home,
    BottomNavigationFragments.Category,
    BottomNavigationFragments.Gallery,
    BottomNavigationFragments.Profile
)