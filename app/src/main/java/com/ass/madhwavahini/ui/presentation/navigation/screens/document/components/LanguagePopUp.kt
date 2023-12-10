package com.ass.madhwavahini.ui.presentation.navigation.screens.document.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ass.madhwavahini.util.translations.TranslationLanguages

@Composable
fun LanguagePopUpBox(
    onClick: (TranslationLanguages) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    Box(
        Modifier.wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = {
            expanded.value = true
        }) {
            Icon(imageVector = Icons.Default.Translate, contentDescription = "Change Language")
        }
    }
    DropdownMenu(
        expanded = expanded.value,
        onDismissRequest = { expanded.value = false }
    ) {
        TranslationLanguages.entries.forEach { language ->
            DropdownMenuItem(text = {
                Text(
                    text = stringResource(id = language.displayName),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }, onClick = {
                expanded.value = false
                onClick(language)
            }
            )
        }
    }
}

