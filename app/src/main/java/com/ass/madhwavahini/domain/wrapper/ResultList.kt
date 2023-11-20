package com.ass.madhwavahini.domain.wrapper

data class ResultList<T>(
    val success: Boolean,
    val data: List<T>? = null,
    val message: String = ""
)