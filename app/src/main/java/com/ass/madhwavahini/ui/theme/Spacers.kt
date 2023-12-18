package com.ass.madhwavahini.ui.theme

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

val sw4 = @Composable { GetSpacerOfWidth(width = 4) }
val sw8 = @Composable { GetSpacerOfWidth(width = 8) }
val sw12 = @Composable { GetSpacerOfWidth(width = 12) }
val sw16 = @Composable { GetSpacerOfWidth(width = 16) }

val sh4 = @Composable { GetSpacerOfHeight(height = 4) }
val sh8 = @Composable { GetSpacerOfHeight(height = 8) }
val sh12 = @Composable { GetSpacerOfHeight(height = 12) }
val sh16 = @Composable { GetSpacerOfHeight(height = 16) }

@Composable
private fun GetSpacerOfWidth(width: Int) = Spacer(modifier = Modifier.width(width.dp))

@Composable
private fun GetSpacerOfHeight(height: Int) = Spacer(modifier = Modifier.height(height.dp))

data class CompactSpacers(
    val default: Dp = 0.dp,
    val extraSmall: Dp = 4.dp,
    val small: Dp = 8.dp,
    val medium: Dp = 16.dp,
    val large: Dp = 32.dp,
    val extraLarge: Dp = 64.dp,
)

val LocalSpacing = compositionLocalOf { CompactSpacers() }

val MaterialTheme.spacing: CompactSpacers
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current