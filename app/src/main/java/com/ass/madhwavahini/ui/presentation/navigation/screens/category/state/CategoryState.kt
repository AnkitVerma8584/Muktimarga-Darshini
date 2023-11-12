package com.ass.madhwavahini.ui.presentation.navigation.screens.category.state

import com.ass.madhwavahini.domain.modals.HomeCategory
import com.ass.madhwavahini.domain.utils.StringUtil

data class CategoryState(
    val isLoading: Boolean = false,
    val data: List<HomeCategory>? = null,
    val error: StringUtil? = null
)
