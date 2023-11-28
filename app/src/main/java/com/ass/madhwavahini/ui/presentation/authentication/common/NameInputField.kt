package com.ass.madhwavahini.ui.presentation.authentication.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.R
import com.ass.madhwavahini.data.Constants.ROUNDED_CORNER_RADIUS

@Composable
fun NameInput(
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    name: String,
    error: String? = null,
    onValueChanged: (name: String) -> Unit
) {
    val trailingIconView = @Composable {
        IconButton(
            onClick = { onValueChanged("") },
        ) {
            Icon(Icons.Default.Clear, contentDescription = null)
        }
    }
    Text(
        text =   stringResource(id = R.string.name_header),
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(0.9f),
        style = MaterialTheme.typography.labelLarge
    )
    OutlinedTextField(shape = RoundedCornerShape(ROUNDED_CORNER_RADIUS),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
            focusedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
            unfocusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.2f),
            focusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.5f),
            unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer
        ),
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .focusRequester(focusRequester),
        value = name,
        placeholder = {
            Text(
                stringResource(id = R.string.name_hint),
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        trailingIcon = if (name.isNotBlank()) trailingIconView else null,
        singleLine = true,
        maxLines = 1,
        onValueChange = {
            onValueChanged(it)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Person, contentDescription = null
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Next
        ),
        keyboardActions = KeyboardActions(onNext = {
            focusManager.moveFocus(FocusDirection.Down)
        }),
        supportingText = {
            error?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        })
}
