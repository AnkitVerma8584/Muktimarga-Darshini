package com.ass.madhwavahini.ui.presentation.navigation.modal

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Book
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.ui.graphics.vector.ImageVector
import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.wrapper.StringUtil

sealed class BottomNavigationFragments(
    val title: StringUtil,
    val route: String,
    val icon: ImageVector
) {
    data object Home : BottomNavigationFragments(
        route = "home",
        title = StringUtil.StringResource(R.string.home),
        icon = Icons.Outlined.Home
    )

    data object Category : BottomNavigationFragments(
        route = "category_home",
        title = StringUtil.StringResource(R.string.category),
        icon = Icons.Outlined.Category
    )

    data object Aradhna : BottomNavigationFragments(
        route = "aradhna",
        title = StringUtil.StringResource(R.string.phone),
        icon = Icons.Outlined.Book
    )

    data object Profile : BottomNavigationFragments(
        route = "profile_home",
        title = StringUtil.StringResource(R.string.about),
        icon = Icons.Outlined.Person
    )
}

val bottomNavigationFragmentsList = listOf(
    BottomNavigationFragments.Home,
    BottomNavigationFragments.Category,
    BottomNavigationFragments.Aradhna,
    BottomNavigationFragments.Profile
)