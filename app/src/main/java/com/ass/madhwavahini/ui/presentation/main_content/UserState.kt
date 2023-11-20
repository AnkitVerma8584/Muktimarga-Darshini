package com.ass.madhwavahini.ui.presentation.main_content

import com.ass.madhwavahini.domain.wrapper.StringUtil

data class UserState(
    val isLoading: Boolean = false,
    val data: String? = null,
    val error: StringUtil? = null
)
