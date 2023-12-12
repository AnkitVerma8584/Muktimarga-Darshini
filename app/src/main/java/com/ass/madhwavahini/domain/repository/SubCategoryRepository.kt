package com.ass.madhwavahini.domain.repository

import com.ass.madhwavahini.domain.modals.HomeSubCategory
import com.ass.madhwavahini.domain.wrapper.UiStateList
import kotlinx.coroutines.flow.Flow

interface SubCategoryRepository {
    fun getSubCategory(categoryId: Int): Flow<UiStateList<HomeSubCategory>>
}