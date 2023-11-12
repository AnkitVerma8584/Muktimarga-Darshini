package com.ass.madhwavahini.domain.repository

import com.ass.madhwavahini.ui.presentation.navigation.screens.file_details.modals.DocumentState
import kotlinx.coroutines.flow.Flow

interface DocumentRepository {

    fun getDocument(
        homeFileName: String,
        homeFileUrl: String
    ): Flow<DocumentState>
}