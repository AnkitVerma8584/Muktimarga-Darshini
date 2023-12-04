package com.ass.madhwavahini.domain.wrapper

data class Result<out T>(
    val success: Boolean = false,
    val message: String = "",
    val data: T? = null
)