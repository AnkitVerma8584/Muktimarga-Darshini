package com.ass.madhwavahini.ui.presentation

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ass.madhwavahini.R

@Composable
internal fun SplashScreen() {
    val infiniteTransition = rememberInfiniteTransition(label = "scale animation")
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        ), label = "scale animation"
    )
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
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
                .scale(scale)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x80FFC107),
                            Color(0x33FFC107),
                            Color(0x00FFEB3B)
                        ),
                    ),
                )
        )
        Image(
            modifier = Modifier.size(350.dp),
            painter = rememberAsyncImagePainter(model = R.drawable.app_logo),
            contentDescription = null
        )
        Text(
            text = "Developed by Aishwarya Software, Bengaluru",
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .padding(8.dp)
                .align(Alignment.BottomCenter)
        )
    }
}
/*Modifier
                .size(280.dp)
                .align(Alignment.Center)
                .scale(1f, 1.2f)
                .scale(scale)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0x80FFC107),
                            Color(0x33FFC107),
                            Color(0x00FFEB3B)
                        ),
                    ),
                )*/