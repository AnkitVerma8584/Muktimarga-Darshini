package com.ass.madhwavahini.ui.presentation.navigation.destinations.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.ui.theme.dimens

@Composable
fun SettingOptions(text: String, icon: ImageVector, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp)
    ) {
        Spacer(modifier = Modifier.width(MaterialTheme.dimens.spacerMedium))
        Icon(
            imageVector = icon,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(MaterialTheme.dimens.spacerMedium))
        Text(text = text, modifier = Modifier.weight(1f))
    }
}

@Composable
fun MyRow(text: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {
        Spacer(modifier = Modifier.width(MaterialTheme.dimens.spacerMedium))
        Icon(imageVector = icon, contentDescription = null)
        Spacer(modifier = Modifier.width(MaterialTheme.dimens.spacerMedium))
        Text(text = text, modifier = Modifier.weight(1f))
    }
}
