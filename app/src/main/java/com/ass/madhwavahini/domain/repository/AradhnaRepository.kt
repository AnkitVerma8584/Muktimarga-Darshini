package com.ass.madhwavahini.domain.repository

import com.ass.madhwavahini.domain.modals.HomeAradhna
import com.ass.madhwavahini.domain.wrapper.UiStateList
import kotlinx.coroutines.flow.Flow

interface AradhnaRepository {
    fun getAradhnas(): Flow<UiStateList<HomeAradhna>>
}