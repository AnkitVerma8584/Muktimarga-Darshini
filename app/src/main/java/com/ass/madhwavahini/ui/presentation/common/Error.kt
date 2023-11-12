package com.ass.madhwavahini.ui.presentation.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.domain.utils.StringUtil


@Composable
fun StringUtil.ShowErrorText() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        text = this.asString(),
        color = MaterialTheme.colorScheme.error
    )
}

@Composable
fun ShowErrorText(error: String) {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        text = error,
        color = MaterialTheme.colorScheme.error
    )
}

@Composable
fun NoSearchedResults() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        text = "No results found for the given query",
        color = MaterialTheme.colorScheme.error
    )
}