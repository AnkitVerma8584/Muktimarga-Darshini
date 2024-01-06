package com.ass.madhwavahini.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class Dimens(
    val default: Dp = 0.dp,
    val extraSmall: TextUnit,
    val small: TextUnit,
    val medium: TextUnit,
    val large: TextUnit,
    val extraLarge: TextUnit,
    val spacerExtraSmall: Dp,
    val spacerSmall: Dp,
    val spacerMedium: Dp,
    val spacerLarge: Dp,
    val spacerExtraLarge: Dp,
    val paddingSmall: Dp,
    val paddingMedium: Dp,
    val paddingLarge: Dp,
    val homeGridSize: Dp,
    val galleryGridSize: Dp,
    val settingsGridSize: Dp,
    val categoryGridSize: Dp,
    val buttonSize: Dp,
    val logoSize: Dp
)

val CompactDimens = Dimens(
    default = 0.dp,
    extraSmall = 0.sp,
    small = 0.sp,
    medium = 0.sp,
    large = 0.sp,
    extraLarge = 0.sp,
    spacerExtraSmall = 3.dp,
    spacerSmall = 6.dp,
    spacerMedium = 12.dp,
    spacerLarge = 16.dp,
    spacerExtraLarge = 24.dp,
    paddingSmall = 2.dp,
    paddingMedium = 4.dp,
    paddingLarge = 8.dp,
    homeGridSize = 120.dp,
    galleryGridSize = 160.dp,
    settingsGridSize = 300.dp,
    categoryGridSize = 120.dp,
    buttonSize = 40.dp,
    logoSize = 120.dp
)

val MediumDimens = Dimens(
    default = 0.dp,
    extraSmall = 0.sp,
    small = 0.sp,
    medium = 0.sp,
    large = 0.sp,
    extraLarge = 0.sp,
    spacerExtraSmall = 4.dp,
    spacerSmall = 8.dp,
    spacerMedium = 16.dp,
    spacerLarge = 32.dp,
    spacerExtraLarge = 48.dp,
    paddingSmall = 4.dp,
    paddingMedium = 8.dp,
    paddingLarge = 16.dp,
    homeGridSize = 150.dp,
    galleryGridSize = 180.dp,
    settingsGridSize = 350.dp,
    categoryGridSize = 150.dp,
    buttonSize = 42.dp,
    logoSize = 180.dp
)

val ExpandedDimens = Dimens(
    default = 0.dp,
    extraSmall = 0.sp,
    small = 0.sp,
    medium = 0.sp,
    large = 0.sp,
    extraLarge = 0.sp,
    spacerExtraSmall = 4.dp,
    spacerSmall = 8.dp,
    spacerMedium = 16.dp,
    spacerLarge = 32.dp,
    spacerExtraLarge = 64.dp,
    paddingSmall = 8.dp,
    paddingMedium = 12.dp,
    paddingLarge = 16.dp,
    homeGridSize = 160.dp,
    galleryGridSize = 250.dp,
    settingsGridSize = 350.dp,
    categoryGridSize = 180.dp,
    buttonSize = 48.dp,
    logoSize = 200.dp
)

val TabDimens = Dimens(
    default = 0.dp,
    extraSmall = 0.sp,
    small = 0.sp,
    medium = 0.sp,
    large = 0.sp,
    extraLarge = 0.sp,
    spacerExtraSmall = 0.dp,
    spacerSmall = 8.dp,
    spacerMedium = 16.dp,
    spacerLarge = 32.dp,
    spacerExtraLarge = 64.dp,
    paddingSmall = 8.dp,
    paddingMedium = 12.dp,
    paddingLarge = 16.dp,
    homeGridSize = 160.dp,
    galleryGridSize = 250.dp,
    settingsGridSize = 350.dp,
    categoryGridSize = 200.dp,
    buttonSize = 52.dp,
    logoSize = 220.dp
)

@Composable
fun ProvideAppUtils(
    appDimens: Dimens,
    content: @Composable () -> Unit,
) {
    val dimens = remember { appDimens }
    CompositionLocalProvider(LocalAppDimens provides dimens) {
        content()
    }
}

val LocalAppDimens = compositionLocalOf { MediumDimens }

val ScreenOrientation
    @ReadOnlyComposable
    @Composable
    get() = LocalConfiguration.current.orientation
