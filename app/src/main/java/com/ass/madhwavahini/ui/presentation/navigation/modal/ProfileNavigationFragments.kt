package com.ass.madhwavahini.ui.presentation.navigation.modal

import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.wrapper.StringUtil

sealed class ProfileNavigationFragments(
    val title: StringUtil,
    val route: String
) {
    data object Profile : ProfileNavigationFragments(
        route = "profile",
        title = StringUtil.StringResource(R.string.category)
    )

    data object AboutUs : ProfileNavigationFragments(
        route = "about",
        title = StringUtil.StringResource(R.string.about)
    )

    data object Support : ProfileNavigationFragments(
        route = "contact",
        title = StringUtil.StringResource(R.string.support)
    )

    data object ContactUs : ProfileNavigationFragments(
        route = "contact",
        title = StringUtil.StringResource(R.string.contact_us)
    )
}
/*

val profileFragmentsList: List<ProfileNavigationFragments> = listOf(
    ProfileNavigationFragments.Profile,
    ProfileNavigationFragments.AboutUs,
    ProfileNavigationFragments.Support,
    ProfileNavigationFragments.ContactUs
)*/
