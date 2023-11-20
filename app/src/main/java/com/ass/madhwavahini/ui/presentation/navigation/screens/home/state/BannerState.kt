package com.ass.madhwavahini.ui.presentation.navigation.screens.home.state

import com.ass.madhwavahini.domain.wrapper.StringUtil

data class BannerState(
    val isLoading: Boolean = false,
    val data: List<String>? = null,
    val error: StringUtil? = null
)
