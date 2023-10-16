package com.ass.muktimargadarshini.ui.presentation.navigation.screens.file_details.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun DocumentText(
    query: String = "",
    text: String? = null,
    scale: Float = 1.0f,
    spanStyle: SpanStyle = SpanStyle(
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        fontWeight = FontWeight.SemiBold,
        background = MaterialTheme.colorScheme.primaryContainer
    )
) {
    text?.let {
        val annotatedString by remember(query) {
            derivedStateOf {
                buildAnnotatedString {
                    var start = 0
                    while (query.length > 2 && it.indexOf(query, start, ignoreCase = true) != -1) {
                        val firstIndex = it.indexOf(query, start, true)
                        val end = firstIndex + query.length
                        append(it.substring(start, firstIndex))
                        withStyle(style = spanStyle) {
                            append(it.substring(firstIndex, end))
                        }
                        start = end
                    }
                    append(it.substring(start, it.length))
                    toAnnotatedString()
                }
            }
        }
        Text(
            modifier = Modifier.padding(horizontal = 8.dp),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            text = annotatedString,
            fontSize = scale.sp,
            lineHeight = scale.sp * 1.5
        )
        Spacer(modifier = Modifier.height(15.dp))
    }
}
