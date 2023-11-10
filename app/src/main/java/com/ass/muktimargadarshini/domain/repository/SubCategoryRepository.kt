package com.ass.muktimargadarshini.domain.repository

import com.ass.muktimargadarshini.ui.presentation.navigation.screens.sub_category.components.SubCategoryState
import kotlinx.coroutines.flow.Flow

interface SubCategoryRepository {
    fun getSubCategory(categoryId: Int): Flow<SubCategoryState>
}