package com.ass.madhwavahini.domain.repository

import com.ass.madhwavahini.domain.wrapper.UiState
import kotlinx.coroutines.flow.Flow
import java.io.File

interface DocumentRepository {

    fun getDocument(
        homeFileName: String,
        homeFileUrl: String
    ): Flow<UiState<File>>
}