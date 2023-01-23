package com.ass.muktimargadarshini.ui.presentation.navigation.modal

import androidx.annotation.DrawableRes
import com.ass.muktimargadarshini.R
import com.ass.muktimargadarshini.domain.utils.StringUtil

sealed class NavigationFragment(
    val route: String,
    var title: StringUtil,
    @DrawableRes val icon: Int? = null
) {
    object Home : NavigationFragment(
        route = "home", title = StringUtil.StringResource(R.string.home), icon = R.drawable.ic_home
    )

    object About : NavigationFragment(
        route = "about",
        title = StringUtil.StringResource(R.string.about),
        icon = R.drawable.ic_about
    )

    object Contact : NavigationFragment(
        route = "contact",
        title = StringUtil.StringResource(R.string.contact_us),
        icon = R.drawable.ic_contact
    )

    object Support : NavigationFragment(
        route = "support",
        title = StringUtil.StringResource(R.string.support),
        icon = R.drawable.ic_support
    )

    object SubCategory : NavigationFragment(
        route = "sub_category/{cat_id}", title = StringUtil.StringResource(R.string.sub_cat)
    )

    object SubToSubCategory : NavigationFragment(
        route = "sub_to_sub_category/{cat_id}/{sub_cat_id}",
        title = StringUtil.StringResource(R.string.sub_cat)
    )

    object Files : NavigationFragment(
        route = "files/{cat_id}/{sub_cat_id}/{sub_to_sub_cat_id}",
        title = StringUtil.StringResource(R.string.files)
    )

    object FileDetails : NavigationFragment(
        route = "file_details?file_id={file_id}&file_name={file_name}&file_url={file_url}&query={query}&index={index}",
        title = StringUtil.StringResource(R.string.files_details)
    )

    object Pdf : NavigationFragment(
        route = "pdf?url={url}",
        title = StringUtil.StringResource(R.string.files_details)
    )

}

