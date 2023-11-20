package com.ass.madhwavahini.ui.presentation.navigation.screens.sub_category.components

import com.ass.madhwavahini.domain.modals.HomeSubCategory
import com.ass.madhwavahini.domain.wrapper.StringUtil

data class SubCategoryState(
    val isLoading: Boolean = false,
    val data: List<HomeSubCategory>? = null,
    val error: StringUtil? = null
)
