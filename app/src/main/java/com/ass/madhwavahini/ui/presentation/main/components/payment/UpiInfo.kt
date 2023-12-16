package com.ass.madhwavahini.ui.presentation.main.components.payment

import android.content.ClipData
import android.content.ClipboardManager
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun UpiInfoField(
    header: String,
    text: String,
    clipboardManager: ClipboardManager,
    prefix: @Composable () -> Unit
) {
    Text(
        text = header,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(),
        style = MaterialTheme.typography.labelLarge
    )
    OutlinedTextField(
        shape = RoundedCornerShape(12.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
            focusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.2f),
            focusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.5f),
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        modifier = Modifier
            .fillMaxWidth(),
        value = text,

        trailingIcon = {
            IconButton(
                onClick = {
                    val clipData = ClipData.newPlainText(header, text)
                    clipboardManager.setPrimaryClip(clipData)
                },
            ) {
                Icon(
                    Icons.Default.ContentCopy, contentDescription = null
                )
            }
        },
        singleLine = true,
        maxLines = 1,
        onValueChange = {},
        leadingIcon = prefix,
        readOnly = true
    )
}