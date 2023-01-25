package com.ass.muktimargadarshini.domain.repository

import com.ass.muktimargadarshini.ui.presentation.navigation.screens.category.state.BannerState
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.category.state.CategoryState
import kotlinx.coroutines.flow.Flow


interface HomeRepository {
    fun getCategoryState(): Flow<CategoryState>

    fun getBannerState(): Flow<BannerState>
}