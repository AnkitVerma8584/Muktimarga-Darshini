package com.ass.madhwavahini.domain.repository

import com.ass.madhwavahini.domain.modals.HomeGallery
import com.ass.madhwavahini.domain.wrapper.UiStateList
import kotlinx.coroutines.flow.Flow


interface GalleryRepository {
    fun getGalleryState(): Flow<UiStateList<HomeGallery>>
}