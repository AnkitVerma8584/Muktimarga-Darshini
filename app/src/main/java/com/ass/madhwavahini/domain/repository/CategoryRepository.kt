package com.ass.madhwavahini.domain.repository

import com.ass.madhwavahini.domain.modals.HomeGallery
import com.ass.madhwavahini.domain.modals.HomeCategory
import com.ass.madhwavahini.domain.wrapper.UiStateList
import kotlinx.coroutines.flow.Flow


interface CategoryRepository {
    fun getCategoryState(): Flow<UiStateList<HomeCategory>>
}