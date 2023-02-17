package com.ass.muktimargadarshini.ui.presentation.navigation.screens.sub_to_sub_category.modal

import com.ass.muktimargadarshini.domain.modals.HomeSubToSubCategory
import com.ass.muktimargadarshini.domain.utils.StringUtil

data class SubToSubCategoryState(
    val isLoading: Boolean = false,
    val data: List<HomeSubToSubCategory>? = null,
    val error: StringUtil? = null
)
