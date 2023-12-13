package com.ass.madhwavahini.ui.presentation.main_content

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyAppBar(
    title: String,
    isPaidCustomer: Boolean,
    onBuyClicked: () -> Unit
) {
    TopAppBar(colors = TopAppBarDefaults.topAppBarColors(
        containerColor = Color.Transparent,
        navigationIconContentColor = MaterialTheme.colorScheme.onBackground
    ), title = {
        Text(
            text = title.trim(),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onBackground
        )
    }, actions = {
        if (!isPaidCustomer) IconButton(onClick = onBuyClicked) {
            Text(
                text = "Buy",
                style = MaterialTheme.typography.titleSmall
            )
        }
    })
}