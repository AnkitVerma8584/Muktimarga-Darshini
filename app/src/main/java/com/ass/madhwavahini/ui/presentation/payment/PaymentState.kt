package com.ass.madhwavahini.ui.presentation.payment

import com.ass.madhwavahini.domain.utils.StringUtil

data class PaymentState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: StringUtil? = null
)
