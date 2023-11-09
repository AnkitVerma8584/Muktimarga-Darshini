package com.ass.muktimargadarshini.ui.presentation.payment

import com.ass.muktimargadarshini.domain.utils.StringUtil

data class PaymentState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: StringUtil? = null
)
