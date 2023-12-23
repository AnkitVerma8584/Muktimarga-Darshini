package com.ass.madhwavahini.domain.repository

import com.ass.madhwavahini.domain.modals.HomePanchanga
import com.ass.madhwavahini.domain.wrapper.UiState
import kotlinx.coroutines.flow.Flow

interface PanchangaRepository {
    fun getPanchanga(): Flow<UiState<HomePanchanga>>
}