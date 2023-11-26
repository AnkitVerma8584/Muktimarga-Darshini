package com.ass.madhwavahini.ui.presentation.authentication.password.components


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun OtpView(
    otpText: String,
    otpError: String? = null,
    charSize: TextUnit = 18.sp,
    containerSize: Dp = charSize.value.dp * 2.5f,
    containerRadius: Dp = 4.dp,
    containerSpacing: Dp = 4.dp,
    otpCount: Int = 6,
    onOtpTextChange: (String) -> Unit
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isFocused by interactionSource.collectIsFocusedAsState()

    Column {
        BasicTextField(
            interactionSource = interactionSource,
            value = otpText,
            onValueChange = {
                if (it.length <= otpCount) {
                    onOtpTextChange.invoke(it)
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            decorationBox = {
                Row(horizontalArrangement = Arrangement.spacedBy(containerSpacing)) {
                    repeat(otpCount) { index ->
                        CharView(
                            isFocused = isFocused,
                            index = index,
                            otpCount = otpCount,
                            text = otpText,
                            charSize = charSize,
                            containerRadius = containerRadius,
                            containerSize = containerSize
                        )
                    }
                }
            }
        )
        Spacer(modifier=Modifier.height(5.dp))
        otpError?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.error
            )
        }
    }
}

@Composable
private fun CharView(
    isFocused: Boolean = false,
    index: Int,
    otpCount: Int,
    text: String,
    charSize: TextUnit,
    containerSize: Dp,
    containerRadius: Dp
) {
    val borderColor =
        if ((index == text.length && isFocused) || (index == otpCount - 1 && text.length == otpCount && isFocused))
            MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.5f)
        else MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.2f)

    val modifier = Modifier
        .size(containerSize)
        .border(
            width = 1.5.dp,
            color = borderColor,
            shape = RoundedCornerShape(containerRadius)
        )
        .clip(RoundedCornerShape(containerRadius))
        .background(color = MaterialTheme.colorScheme.secondaryContainer)

    val char = when {
        index >= text.length -> ""
        else -> text[index].toString()
    }
    Text(
        text = char,
        color = MaterialTheme.colorScheme.onSecondaryContainer,
        modifier = modifier.wrapContentHeight(),
        style = MaterialTheme.typography.bodyLarge,
        fontSize = charSize,
        textAlign = TextAlign.Center,
    )
}
