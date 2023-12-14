package com.ass.madhwavahini.domain.repository

import com.ass.madhwavahini.domain.modals.Gallery
import com.ass.madhwavahini.domain.modals.HomeCategory
import com.ass.madhwavahini.domain.wrapper.UiStateList
import kotlinx.coroutines.flow.Flow


interface HomeRepository {
    fun getCategoryState(): Flow<UiStateList<HomeCategory>>

    fun getBannerState(): Flow<UiStateList<Gallery>>
}