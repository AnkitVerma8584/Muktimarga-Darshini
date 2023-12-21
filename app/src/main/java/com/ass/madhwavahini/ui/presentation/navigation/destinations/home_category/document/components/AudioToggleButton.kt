package com.ass.madhwavahini.ui.presentation.navigation.destinations.home_category.document.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.ui.theme.dimens

@Composable
fun BoxScope.AudioToggleButton(
    paddingValues: PaddingValues = PaddingValues(
        top = 76.dp,
        end = MaterialTheme.dimens.paddingLarge
    ),
    isDisplayingAudio: Boolean,
    onButtonToggled: () -> Unit
) {
    IconButton(
        modifier = Modifier
            .align(Alignment.TopEnd)
            .padding(paddingValues)
            .size(MaterialTheme.dimens.buttonSize)
            .clip(CircleShape)
            .background(color = MaterialTheme.colorScheme.primary),
        onClick = onButtonToggled
    ) {
        Icon(
            imageVector = if (isDisplayingAudio) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
            tint = MaterialTheme.colorScheme.onPrimary,
            contentDescription = "Toggle media bar"
        )
    }
}