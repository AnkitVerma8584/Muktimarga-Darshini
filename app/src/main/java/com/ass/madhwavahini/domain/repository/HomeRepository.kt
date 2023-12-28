package com.ass.madhwavahini.domain.repository

import com.ass.madhwavahini.domain.wrapper.UiState
import kotlinx.coroutines.flow.Flow


interface HomeRepository {
    fun getQuoteState(): Flow<UiState<String>>

}