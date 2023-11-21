package com.ass.madhwavahini.ui.presentation.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.R
import com.ass.madhwavahini.domain.wrapper.StringUtil

@Composable
fun NoSearchedResults() {
    Text(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal=16.dp, vertical = 5.dp),
        text = "No results found for the given query",
        color = MaterialTheme.colorScheme.error
    )
}


@Composable
fun StringUtil.ShowError() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.error), contentDescription = null
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(0.75f)
                .padding(8.dp),
            text = this@ShowError.asString(),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.error,
            textAlign = TextAlign.Center
        )
    }
}
