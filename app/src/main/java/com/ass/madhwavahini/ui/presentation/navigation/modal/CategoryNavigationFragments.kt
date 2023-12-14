package com.ass.madhwavahini.ui.presentation.navigation.modal

import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.wrapper.StringUtil

sealed class CategoryNavigationFragments(
    var title: StringUtil,
    val route: String
) {
    data object Category : CategoryNavigationFragments(
        route = "category",
        title = StringUtil.StringResource(R.string.category)
    )

    data object SubCategory : CategoryNavigationFragments(
        route = "sub_category/{cat_id}", title = StringUtil.StringResource(R.string.sub_cat)
    )

    data object SubToSubCategory : CategoryNavigationFragments(
        route = "sub_to_sub_category/{cat_id}/{sub_cat_id}",
        title = StringUtil.StringResource(R.string.sub_cat)
    )

    data object Files : CategoryNavigationFragments(
        route = "files/{cat_id}/{sub_cat_id}/{sub_to_sub_cat_id}",
        title = StringUtil.StringResource(R.string.files)
    )

    data object FileDetails : CategoryNavigationFragments(
        route = "file_details?file_id={file_id}&file_url={file_url}&audio_url={audio_url}&audio_image={audio_image}&query={query}&index={index}",
        title = StringUtil.StringResource(R.string.files_details)
    )

    data object Pdf : CategoryNavigationFragments(
        route = "pdf?file_id={file_id}&file_url={file_url}&audio_url={audio_url}&audio_image={audio_image}",
        title = StringUtil.StringResource(R.string.files_details)
    )
}
/*

val categoryFragmentsList: List<CategoryNavigationFragments> = listOf(
    CategoryNavigationFragments.Category,
    CategoryNavigationFragments.SubCategory,
    CategoryNavigationFragments.Files,
    CategoryNavigationFragments.SubToSubCategory,
    CategoryNavigationFragments.FileDetails,
    CategoryNavigationFragments.Pdf
)
*/
