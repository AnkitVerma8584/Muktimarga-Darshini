package com.ass.madhwavahini.ui.presentation.navigation.screens.sub_to_sub_category.modal

import com.ass.madhwavahini.domain.modals.HomeSubToSubCategory
import com.ass.madhwavahini.domain.utils.StringUtil

data class SubToSubCategoryState(
    val isLoading: Boolean = false,
    val data: List<HomeSubToSubCategory>? = null,
    val error: StringUtil? = null
)
