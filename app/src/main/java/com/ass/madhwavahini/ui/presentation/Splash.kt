package com.ass.madhwavahini.ui.presentation

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.ass.madhwavahini.BuildConfig
import com.ass.madhwavahini.R

@Composable
internal fun SplashScreen(
    sharedImage: @Composable () -> Unit
) {

    val infiniteTransition = rememberInfiniteTransition(label = "scale animation")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f, targetValue = 1.2f, animationSpec = infiniteRepeatable(
            animation = tween(1000), repeatMode = RepeatMode.Reverse
        ), label = "scale animation"
    )
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            Modifier
                .size(280.dp)
                .align(Alignment.Center)
                .scale(1f, 1.2f)
                .graphicsLayer {
                    this.scaleX = scale
                    this.scaleY = scale
                }
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x80FFC107),
                            Color(0x33FFC107),
                            Color(0x00FFEB3B)
                        ),
                    ),
                ))
        sharedImage()
        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(8.dp)
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.splash_description),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )
            Text(
                text = stringResource(id = R.string.app_version, BuildConfig.VERSION_NAME),
                style = MaterialTheme.typography.labelLarge,
                fontWeight = FontWeight.Normal
            )
        }
    }
}

