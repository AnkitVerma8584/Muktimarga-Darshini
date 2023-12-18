package com.ass.madhwavahini.ui.presentation.navigation.modal

import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.wrapper.StringUtil

sealed class HomeNavigationFragments(
    val title: StringUtil,
    val route: String
) {
    data object Home : HomeNavigationFragments(
        route = "home",
        title = StringUtil.StringResource(R.string.category)
    )

    data object Aradhna : HomeNavigationFragments(
        route = "aradhna",
        title = StringUtil.StringResource(R.string.aradhna)
    )

    data object Panchanga : HomeNavigationFragments(
        route = "panchanga",
        title = StringUtil.StringResource(R.string.panchanga)
    )
}

val homeNavigationFragmentsList = listOf(
    HomeNavigationFragments.Home,
    HomeNavigationFragments.Aradhna,
    HomeNavigationFragments.Panchanga
)