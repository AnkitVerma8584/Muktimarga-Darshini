package com.ass.madhwavahini.domain.wrapper

data class UiStateList<T>(
    val isLoading: Boolean = false,
    val data: List<T>? = null,
    val error: StringUtil? = null
)
