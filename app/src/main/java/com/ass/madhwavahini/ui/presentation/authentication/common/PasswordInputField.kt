package com.ass.madhwavahini.ui.presentation.authentication.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun PasswordInput(
    focusRequester: FocusRequester,
    focusManager: FocusManager,
    password: String,
    hint: String = "Enter your password",
    passwordError: String? = null,
    onDoneClicked: () -> Unit,
    onValueChanged: (password: String) -> Unit
) {
    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current

    var passwordVisible by rememberSaveable {
        mutableStateOf(false)
    }
    val image = if (passwordVisible) Icons.Filled.Visibility
    else Icons.Filled.VisibilityOff
    val description = if (passwordVisible) "Hide password" else "Show password"

    val trailingIconView = @Composable {
        IconButton(onClick = {
            passwordVisible = !passwordVisible
        }) {
            Icon(imageVector = image, description)
        }
    }
    Text(
        text = "Password",
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .fillMaxWidth(0.9f),
        style = MaterialTheme.typography.labelLarge
    )
    OutlinedTextField(shape = RoundedCornerShape(12.dp),
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
            .focusRequester(focusRequester)
            .layoutId("password"),
        value = password,
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
        placeholder = {
            Text(
                hint,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        trailingIcon = if (password.isNotBlank()) trailingIconView else null,
        singleLine = true,
        maxLines = 1,
        onValueChange = {
            onValueChanged(it)
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.Lock, contentDescription = null
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false,
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
            focusManager.clearFocus()
            onDoneClicked.invoke()
        }),
        supportingText = {
            passwordError?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.error
                )
            }
        })
}
