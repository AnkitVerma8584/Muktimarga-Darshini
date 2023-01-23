package com.ass.muktimargadarshini.domain.utils

data class ResultList<T>(
    val success: Boolean,
    val data: List<T>? = null,
    val message: String = ""
)