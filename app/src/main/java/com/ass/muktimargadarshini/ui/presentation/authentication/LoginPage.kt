package com.ass.muktimargadarshini.ui.presentation.authentication

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.ass.muktimargadarshini.R

@Composable
fun MobileAuthenticationPage(
    viewModel: LoginViewModel = hiltViewModel()
) {
    val mobile = viewModel.mobile.collectAsStateWithLifecycle().value
    val mobileError = viewModel.mobileError.collectAsStateWithLifecycle().value

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            modifier = Modifier.size(250.dp),
            painter = rememberAsyncImagePainter(model = R.drawable.app_logo),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(50.dp))
        OtpInput(
            mobile = mobile,
            error = mobileError,
            onDoneClicked = viewModel::getOtp,
            onValueChanged = viewModel::setMobile
        )
        Spacer(modifier = Modifier.height(50.dp))
        Button(onClick = {
            viewModel.getOtp(mobile)
        }) {
            Text(
                text = "Get Otp", style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun MobileInput(
    mobile: String,
    hint: String = "Enter mobile number",
    error: String? = null,
    onDoneClicked: (mobile: String) -> Unit,
    onValueChanged: (mobile: String) -> Unit
) {
    val trailingIconView = @Composable {
        IconButton(
            onClick = {
                onValueChanged("")
            },
        ) {
            Icon(
                Icons.Default.Clear, contentDescription = null
            )
        }
    }
    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
    val focusRequester: FocusRequester = remember { FocusRequester() }
    val focusManager: FocusManager = LocalFocusManager.current
    Text(
        text = "Mobile Number",
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
            Icon(
                imageVector = Icons.Filled.Phone, contentDescription = null
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false,
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
            focusManager.clearFocus()
            onDoneClicked.invoke(mobile)
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


@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun OtpInput(
    mobile: String,
    hint: String = "Enter otp",
    error: String? = null,
    onDoneClicked: (otp: String) -> Unit,
    onValueChanged: (otp: String) -> Unit
) {
    val trailingIconView = @Composable {
        IconButton(
            onClick = {
                onValueChanged("")
            },
        ) {
            Icon(
                Icons.Default.Clear, contentDescription = null
            )
        }
    }
    val keyboardController: SoftwareKeyboardController? = LocalSoftwareKeyboardController.current
    val focusRequester: FocusRequester = remember { FocusRequester() }
    val focusManager: FocusManager = LocalFocusManager.current
    Text(
        text = "Otp",
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
            Icon(
                imageVector = Icons.Filled.Phone, contentDescription = null
            )
        },
        keyboardOptions = KeyboardOptions.Default.copy(
            capitalization = KeyboardCapitalization.None,
            autoCorrect = false,
            keyboardType = KeyboardType.Phone,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
            focusManager.clearFocus()
            onDoneClicked.invoke(mobile)
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

