package com.ass.madhwavahini.ui.presentation.authentication.common

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.ui.theme.ShowPreview

@Composable
fun PlaceHolderLoading() {
    CircularProgressIndicator(
        modifier = Modifier
            .size(48.dp),
        strokeCap = StrokeCap.Round
    )
}

@Preview
@Composable
fun LoadingPreview() {
    ShowPreview {
        PlaceHolderLoading()
    }
}