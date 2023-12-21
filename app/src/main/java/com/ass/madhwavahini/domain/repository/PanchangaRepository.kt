package com.ass.madhwavahini.domain.repository

import com.ass.madhwavahini.domain.modals.HomePanchanga
import com.ass.madhwavahini.domain.wrapper.UiStateList
import kotlinx.coroutines.flow.Flow

interface PanchangaRepository {
    fun getPanchangaList(): Flow<UiStateList<HomePanchanga>>
}