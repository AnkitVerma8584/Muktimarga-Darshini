package com.ass.madhwavahini.ui.presentation.navigation.destinations.home_category.document.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.util.translations.TranslationLanguages

@Composable
fun LanguagePopUpBox(
    onClick: (TranslationLanguages) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }

    IconButton(
        onClick = {  expanded.value = true }, modifier = Modifier
            .size(54.dp)
            .padding(8.dp)
            .clip(CircleShape)
            .background(color = MaterialTheme.colorScheme.primary)
    ) {
        Icon(
            imageVector = Icons.Default.Translate,
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = "Change Language"
        )
    }
    /*Box(
        Modifier.wrapContentSize(Alignment.BottomEnd)
    ) {
        IconButton(onClick = {
            expanded.value = true
        }) {
            Icon(imageVector = Icons.Default.Translate, contentDescription = "Change Language")
        }
    }*/

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

