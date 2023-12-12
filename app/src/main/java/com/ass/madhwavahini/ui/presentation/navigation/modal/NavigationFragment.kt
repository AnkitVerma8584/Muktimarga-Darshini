package com.ass.madhwavahini.ui.presentation.navigation.modal

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.PhoneInTalk
import androidx.compose.ui.graphics.vector.ImageVector
import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.wrapper.StringUtil

sealed class NavigationFragment(
    val route: String,
    var title: StringUtil,
    val icon: ImageVector? = null
) {
    data object Home : NavigationFragment(
        route = "home",
        title = StringUtil.StringResource(R.string.home),
        icon = Icons.Outlined.Home
    )


    data object About : NavigationFragment(
        route = "about",
        title = StringUtil.StringResource(R.string.about),
        icon = Icons.Outlined.Info
    )

    data object Contact : NavigationFragment(
        route = "contact",
        title = StringUtil.StringResource(R.string.contact_us),
        icon = Icons.Outlined.PhoneInTalk
    )

    data object Support : NavigationFragment(
        route = "support",
        title = StringUtil.StringResource(R.string.support),
        icon = Icons.Default.SupportAgent
    )

    data object Category : NavigationFragment(
        route = "category",
        title = StringUtil.StringResource(R.string.category)
    )

    data object SubCategory : NavigationFragment(
        route = "sub_category/{cat_id}", title = StringUtil.StringResource(R.string.sub_cat)
    )

    data object SubToSubCategory : NavigationFragment(
        route = "sub_to_sub_category/{cat_id}/{sub_cat_id}",
        title = StringUtil.StringResource(R.string.sub_cat)
    )

    data object Files : NavigationFragment(
        route = "files/{cat_id}/{sub_cat_id}/{sub_to_sub_cat_id}",
        title = StringUtil.StringResource(R.string.files)
    )

    data object FileDetails : NavigationFragment(
        route = "file_details?file_id={file_id}&file_url={file_url}&audio_url={audio_url}&audio_image={audio_image}&query={query}&index={index}",
        title = StringUtil.StringResource(R.string.files_details)
    )

    data object Pdf : NavigationFragment(
        route = "pdf?file_id={file_id}&file_url={file_url}&audio_url={audio_url}&audio_image={audio_image}",
        title = StringUtil.StringResource(R.string.files_details)
    )
}

