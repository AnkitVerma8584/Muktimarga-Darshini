package com.ass.madhwavahini.ui.presentation.common

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp


enum class SnackBarType {
    NORMAL, WARNING, ERROR;

    val color: Pair<Color, Color>
        @Composable @ReadOnlyComposable get() = when (this) {
            NORMAL -> Pair(
                MaterialTheme.colorScheme.secondaryContainer,
                MaterialTheme.colorScheme.onSecondaryContainer
            )

            WARNING -> Pair(
                MaterialTheme.colorScheme.tertiaryContainer,
                MaterialTheme.colorScheme.onTertiaryContainer
            )

            ERROR -> Pair(
                MaterialTheme.colorScheme.errorContainer,
                MaterialTheme.colorScheme.onErrorContainer
            )
        }

    companion object {
        fun getType(actionLabel: String?): SnackBarType {
            return when (actionLabel) {
                ERROR.name -> ERROR
                WARNING.name -> WARNING
                else -> NORMAL
            }
        }
    }
}

@Composable
fun MyCustomSnack(
    text: String,
    snackBarType: SnackBarType = SnackBarType.NORMAL,
    onDismiss: () -> Unit
) {
    Snackbar(containerColor = snackBarType.color.first,
        contentColor = snackBarType.color.second,
        dismissActionContentColor = snackBarType.color.second,
        dismissAction = {
            IconButton(onClick = onDismiss) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Dismiss Error"
                )
            }
        }) {
        Text(
            modifier = Modifier.padding(5.dp),
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Normal
        )
    }
}
