package com.ass.muktimargadarshini.ui.presentation.navigation.screens.category.state

import com.ass.muktimargadarshini.domain.utils.StringUtil

data class BannerState(
    val isLoading: Boolean = false,
    val data: List<String>? = null,
    val error: StringUtil? = null
)
