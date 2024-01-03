package com.ass.madhwavahini.ui.presentation.authentication.common

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import com.ass.madhwavahini.ui.theme.ShowPreview
import com.ass.madhwavahini.ui.theme.dimens

@Composable
fun PlaceHolderLoading() {
    CircularProgressIndicator(
        modifier = Modifier
            .size(MaterialTheme.dimens.buttonSize),
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