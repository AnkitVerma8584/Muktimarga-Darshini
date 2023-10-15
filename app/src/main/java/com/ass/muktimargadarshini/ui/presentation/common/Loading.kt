package com.ass.muktimargadarshini.ui.presentation.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun Loading() {
    Box(modifier = Modifier.fillMaxWidth())
    {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
    }
}