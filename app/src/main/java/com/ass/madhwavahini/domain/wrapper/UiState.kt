package com.ass.madhwavahini.domain.wrapper

data class UiState<T>(
    val isLoading: Boolean = false,
    val data: T? = null,
    val error: StringUtil? = null
)
