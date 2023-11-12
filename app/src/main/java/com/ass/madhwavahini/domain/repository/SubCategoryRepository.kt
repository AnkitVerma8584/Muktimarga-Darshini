package com.ass.madhwavahini.domain.repository

import com.ass.madhwavahini.ui.presentation.navigation.screens.sub_category.components.SubCategoryState
import kotlinx.coroutines.flow.Flow

interface SubCategoryRepository {
    fun getSubCategory(categoryId: Int): Flow<SubCategoryState>
}