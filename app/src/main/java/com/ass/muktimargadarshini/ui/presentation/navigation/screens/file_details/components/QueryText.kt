package com.ass.muktimargadarshini.ui.presentation.navigation.screens.file_details.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
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
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.file_details.modals.FileDocumentText

@Composable
fun SearchedText(
    query: String,
    content: FileDocumentText,
    onClick: (index: Int) -> Unit,
    spanStyle: SpanStyle = SpanStyle(
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        fontWeight = FontWeight.SemiBold,
        background = MaterialTheme.colorScheme.primaryContainer
    )
) {
    val text = content.text
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .clickable { onClick(content.index) },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onBackground,
            text = annotatedString
        )
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
    }
}
