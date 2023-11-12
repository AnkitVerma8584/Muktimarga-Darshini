package com.ass.madhwavahini.domain.utils

data class Result<T>(
    val success: Boolean = false,
    val message: String = "",
    val data: T? = null
)