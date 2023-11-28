package com.ass.madhwavahini.ui.presentation.common

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle


@Composable
fun getAnnotatedText(
    text: String,
    query: String,
    spanStyle: SpanStyle = SpanStyle(
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        background = MaterialTheme.colorScheme.primaryContainer
    )
): AnnotatedString =
    buildAnnotatedString {
        var start = 0
        while (query.isNotBlank() && text.indexOf(query, start, ignoreCase = true) != -1) {
            val firstIndex = text.indexOf(query, start, true)
            val end = firstIndex + query.length
            append(text.substring(start, firstIndex))
            withStyle(style = spanStyle) {
                append(text.substring(firstIndex, end))
            }
            start = end
        }
        append(text.substring(start, text.length))
        toAnnotatedString()
    }