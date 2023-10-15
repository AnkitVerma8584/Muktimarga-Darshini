package com.ass.muktimargadarshini.ui.presentation.navigation.screens.file_details.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun BoxScope.ScrollToTopButton(
    listState: LazyListState,
    coroutineScope: CoroutineScope
) {
    val shouldShowButton = remember {
        derivedStateOf { listState.firstVisibleItemIndex > 0 }
    }
    AnimatedVisibility(
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(16.dp),
        visible = shouldShowButton.value,
        enter = slideInVertically(animationSpec = tween(400), initialOffsetY = { it }) + fadeIn(),
        exit = fadeOut(),
        content = {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        listState.animateScrollToItem(index = 0)
                    }
                }, modifier = Modifier
                    .size(54.dp)
                    .padding(8.dp)
                    .clip(CircleShape)
                    .background(color = MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowUp,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    contentDescription = null
                )
            }
        })
}