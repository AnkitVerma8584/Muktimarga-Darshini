package com.ass.muktimargadarshini.presentation.ui.navigation.screens.sub_category

import com.ass.muktimargadarshini.domain.modals.HomeSubCategory
import com.ass.muktimargadarshini.domain.utils.StringUtil

data class SubCategoryState(
    val isLoading: Boolean = false,
    val data: List<HomeSubCategory>? = null,
    val error: StringUtil? = null
)
