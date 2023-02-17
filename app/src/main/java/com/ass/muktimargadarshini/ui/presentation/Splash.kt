package com.ass.muktimargadarshini.ui.presentation

import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.ass.muktimargadarshini.R

@Composable
internal fun SplashScreen() {
    val infiniteTransition = rememberInfiniteTransition()
    val scale by infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000),
            repeatMode = RepeatMode.Reverse
        )
    )
    Box(
        contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()
    ) {
        Box(
            Modifier
                .scale(scale)
                .size(250.dp)
                .clip(CircleShape)
                .background(
                    brush = Brush.radialGradient(
                        colors = listOf(
                            Color(0xCCFFC107),
                            Color(0x33FFC107),
                            Color(0x03FFEB3B)
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
            text = "Powered by Dtech Bengaluru",
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.BottomCenter)
        )
    }

}
