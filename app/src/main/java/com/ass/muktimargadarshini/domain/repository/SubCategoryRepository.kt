package com.ass.muktimargadarshini.domain.repository

import com.ass.muktimargadarshini.presentation.ui.navigation.screens.sub_category.SubCategoryState
import kotlinx.coroutines.flow.Flow

interface SubCategoryRepository {
    fun getSubCategory(categoryId: Int): Flow<SubCategoryState>
}