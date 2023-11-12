package com.ass.madhwavahini.ui.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex

@Composable
fun Loading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .zIndex(3f)
    )
    {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}