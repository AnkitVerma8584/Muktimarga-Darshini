package com.ass.madhwavahini.domain.repository

import com.ass.madhwavahini.ui.presentation.navigation.screens.home.state.BannerState
import com.ass.madhwavahini.ui.presentation.navigation.screens.home.state.CategoryState
import kotlinx.coroutines.flow.Flow


interface HomeRepository {
    fun getCategoryState(): Flow<CategoryState>

    fun getBannerState(): Flow<BannerState>
}