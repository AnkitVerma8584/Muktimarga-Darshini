package com.ass.madhwavahini.ui.theme

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview(
    name = "small font",
    group = "font scales",
    fontScale = 0.5f,
)
@Preview(
    name = "large font",
    group = "font scales",
    fontScale = 1.5f
)
annotation class FontScalePreviews

@Preview(
    name = "light mode",
    group = "ui",
    uiMode = UI_MODE_NIGHT_NO,
)
@Preview(
    name = "dark mode",
    group = "ui",
    uiMode = UI_MODE_NIGHT_YES
)
annotation class UiModePreviews

@Preview(
    name = "default",
    group = "devices",
    device = "id:pixel_5"
)
@Preview(
    name = "phone",
    group = "devices",
    device = "spec:width=411dp,height=891dp"
)
@Preview(
    name = "foldable",
    group = "devices",
    device = "spec:width=673.5dp,height=841dp,dpi=480"
)
@Preview(
    name = "tablet",
    group = "devices",
    device = "spec:width=1280dp,height=800dp,dpi=480"
)
@Preview(
    name = "desktop",
    group = "devices",
    device = "spec:width=1920dp,height=1080dp,dpi=480"
)
annotation class DevicePreviews

@Composable
fun ShowPreview(
    content: @Composable () -> Unit
) {
    MadhwaVahiniTheme(
        content = {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background,
                content = content
            )
        }
    )
}
