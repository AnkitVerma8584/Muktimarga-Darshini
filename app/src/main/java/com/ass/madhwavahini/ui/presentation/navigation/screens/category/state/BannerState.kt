package com.ass.madhwavahini.ui.presentation.navigation.screens.category.state

import com.ass.madhwavahini.domain.utils.StringUtil

data class BannerState(
    val isLoading: Boolean = false,
    val data: List<String>? = null,
    val error: StringUtil? = null
)
