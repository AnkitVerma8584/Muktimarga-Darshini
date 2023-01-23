package com.ass.muktimargadarshini.ui.presentation.navigation.screens.category.state

import com.ass.muktimargadarshini.domain.modals.HomeCategory
import com.ass.muktimargadarshini.domain.utils.StringUtil

data class CategoryState(
    val isLoading: Boolean = false,
    val data: List<HomeCategory>? = null,
    val error: StringUtil? = null
)
