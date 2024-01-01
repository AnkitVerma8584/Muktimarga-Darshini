package com.ass.madhwavahini.util

import android.app.Activity
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable

class MyWindowSizeClass(private val activity: Activity) {

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    @Composable
    fun GetScreenType() {
        val sizeClass: WindowSizeClass = calculateWindowSizeClass(activity)
        val widthSizeClass: WindowWidthSizeClass = sizeClass.widthSizeClass
        val heightSizeClass: WindowHeightSizeClass = sizeClass.heightSizeClass

        if (widthSizeClass == WindowWidthSizeClass.Compact && heightSizeClass == WindowHeightSizeClass.Compact){

        }



    }
}

enum class ScreenType{
    CompactPortrait,
    CompactLandscape,
    MediumPortrait
}