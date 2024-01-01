package com.ass.madhwavahini.domain.modals

data class HomeQuotes(
    val title: String,
    val description: String
) {
    fun isEmpty(): Boolean = title.isBlank() || description.isBlank()
}
