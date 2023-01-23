package com.ass.muktimargadarshini.presentation.ui.navigation.screens.sub_to_sub_category

import com.ass.muktimargadarshini.domain.modals.HomeSubToSubCategory
import com.ass.muktimargadarshini.domain.utils.StringUtil

data class SubToSubCategoryState(
    val isLoading: Boolean = false,
    val data: List<HomeSubToSubCategory>? = null,
    val error: StringUtil? = null
)
