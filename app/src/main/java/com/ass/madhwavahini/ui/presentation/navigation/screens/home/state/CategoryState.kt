package com.ass.madhwavahini.ui.presentation.navigation.screens.home.state

import com.ass.madhwavahini.domain.modals.HomeCategory
import com.ass.madhwavahini.domain.wrapper.StringUtil

data class CategoryState(
    val isLoading: Boolean = false,
    val data: List<HomeCategory>? = null,
    val error: StringUtil? = null
)
