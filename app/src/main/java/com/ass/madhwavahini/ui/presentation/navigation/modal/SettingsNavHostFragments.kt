package com.ass.madhwavahini.ui.presentation.navigation.modal

import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.wrapper.StringUtil

sealed class SettingsNavHostFragments(
    val title: StringUtil,
    val route: String
) {
    data object Settings : SettingsNavHostFragments(
        route = "settings",
        title = StringUtil.StringResource(R.string.settings)
    )

    data object AboutUs : SettingsNavHostFragments(
        route = "about",
        title = StringUtil.StringResource(R.string.about)
    )

    data object Support : SettingsNavHostFragments(
        route = "support",
        title = StringUtil.StringResource(R.string.support)
    )

    data object ContactUs : SettingsNavHostFragments(
        route = "contact",
        title = StringUtil.StringResource(R.string.contact_us)
    )
}

val settingsFragmentList: List<SettingsNavHostFragments> = listOf(
    SettingsNavHostFragments.Settings,
    SettingsNavHostFragments.AboutUs,
    SettingsNavHostFragments.Support,
    SettingsNavHostFragments.ContactUs
)
