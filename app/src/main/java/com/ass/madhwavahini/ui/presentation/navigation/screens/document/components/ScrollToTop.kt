package com.ass.madhwavahini.ui.presentation.navigation.screens.document.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.util.translations.TranslationLanguages

@Composable
fun BoxScope.ScrollToTopButton(
    shouldScrollToTop: Boolean,
    onClick: () -> Unit,
    onTranslateClick: (TranslationLanguages) -> Unit
) {
    Column(
        modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(16.dp),
    ) {
        AnimatedVisibility(
            visible = shouldScrollToTop,
            enter = slideInVertically(
                animationSpec = tween(400),
                initialOffsetY = { it }) + fadeIn(),
            exit = slideOutVertically(
                animationSpec = tween(400),
                targetOffsetY = { it }) + fadeOut(),
            content = {
                IconButton(
                    onClick = onClick, modifier = Modifier
                        .size(54.dp)
                        .padding(8.dp)
                        .clip(CircleShape)
                        .background(color = MaterialTheme.colorScheme.primary)
                ) {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowUp,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        contentDescription = "Scroll to top"
                    )
                }
            })

        LanguagePopUpBox(onClick = onTranslateClick)
    }
}