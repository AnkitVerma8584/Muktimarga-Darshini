package com.ass.madhwavahini.ui.presentation.authentication.model

import com.ass.madhwavahini.domain.modals.User
import com.ass.madhwavahini.domain.wrapper.StringUtil

data class LoginState(
    val isLoading: Boolean = false,
    val data: User? = null,
    val error: StringUtil? = null
)
