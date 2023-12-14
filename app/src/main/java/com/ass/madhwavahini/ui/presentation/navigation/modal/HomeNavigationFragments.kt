package com.ass.madhwavahini.ui.presentation.navigation.modal

import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.wrapper.StringUtil

sealed class HomeNavigationFragments(
    val title: StringUtil,
    val route: String
) {
    data object Home : ProfileNavigationFragments(
        route = "home",
        title = StringUtil.StringResource(R.string.category)
    )

    data object Aradhna : ProfileNavigationFragments(
        route = "about",
        title = StringUtil.StringResource(R.string.about)
    )

    data object Panchanga : ProfileNavigationFragments(
        route = "panchanga",
        title = StringUtil.StringResource(R.string.support)
    )
}
