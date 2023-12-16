package com.ass.madhwavahini.ui.presentation.navigation.destinations.home_category.document.components

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
    text: String,
    query: String,
    scale: Float,
    spanStyle: SpanStyle = SpanStyle(
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        fontWeight = FontWeight.SemiBold,
        background = MaterialTheme.colorScheme.primaryContainer
    )
) {
    val annotatedString by remember(query) {
        derivedStateOf {
            buildAnnotatedString {
                var start = 0
                while (query.length > 2 && text.indexOf(
                        query,
                        start,
                        ignoreCase = true
                    ) != -1
                ) {
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
        }
    }
    Text(
        modifier = Modifier.padding(horizontal = 8.dp),
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onBackground,
        text = annotatedString,
        fontSize = scale.sp,
        lineHeight = scale.sp * 1.6
    )
    Spacer(modifier = Modifier.height(8.dp))

}
