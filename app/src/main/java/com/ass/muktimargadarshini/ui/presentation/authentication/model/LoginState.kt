package com.ass.muktimargadarshini.ui.presentation.authentication.model

import com.ass.muktimargadarshini.domain.modals.User
import com.ass.muktimargadarshini.domain.utils.StringUtil

data class LoginState(
    val isLoading: Boolean = false,
    val data: User? = null,
    val error: StringUtil? = null
)
