package com.ass.madhwavahini.ui.presentation.navigation.destinations.home.panchanga

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ass.madhwavahini.ui.theme.dimens

@Composable
fun PanchangaPage(
    panchangaViewModel: PanchangaViewModel = hiltViewModel()
) {
    val panchangas by panchangaViewModel.panchangaState.collectAsStateWithLifecycle()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = MaterialTheme.dimens.paddingLarge,
                end = MaterialTheme.dimens.paddingLarge,
                bottom = MaterialTheme.dimens.paddingLarge
            )
    ) {
        Text(text = "panchanga")


    }
}