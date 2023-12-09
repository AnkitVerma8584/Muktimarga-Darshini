package com.ass.madhwavahini.ui.presentation.authentication.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.R
import com.ass.madhwavahini.data.Constants.ROUNDED_CORNER_RADIUS

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun MobileInput(
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    mobile: String,
    error: String? = null,
    onValueChanged: (mobile: String) -> Unit,
    label: String = stringResource(id = R.string.phone_header),
    hint: String = stringResource(id = R.string.phone_hint),
    imeAction: ImeAction = ImeAction.Next,
    onDoneClick: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        capitalization = KeyboardCapitalization.None,
        autoCorrect = false,
        keyboardType = KeyboardType.Phone,
        imeAction = imeAction
    ),
    keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
) {
    val trailingIconView = @Composable {
        IconButton(
            onClick = {
                onValueChanged("")
            },
        ) {
            Icon(Icons.Default.Clear, contentDescription = null)
        }
    }
    Text(
        text = label,
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(0.9f),
        style = MaterialTheme.typography.labelLarge
    )
    OutlinedTextField(
        shape = RoundedCornerShape(ROUNDED_CORNER_RADIUS),
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
        value = mobile,
        placeholder = {
            Text(
                hint,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        trailingIcon = if (mobile.isNotBlank()) trailingIconView else null,
        singleLine = true,
        maxLines = 1,
        onValueChange = {
            if (it.length <= 10) onValueChanged(it)
        },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Phone, contentDescription = null)
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = {
                keyboardController?.hide()
                focusManager.clearFocus()
                if (onDoneClick != null) {
                    onDoneClick()
                }
            },
            onNext = {
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
        }
    )
}
