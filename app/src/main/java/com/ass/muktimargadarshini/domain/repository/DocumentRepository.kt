package com.ass.muktimargadarshini.domain.repository

import com.ass.muktimargadarshini.ui.presentation.navigation.screens.file_details.modals.DocumentState
import kotlinx.coroutines.flow.Flow

interface DocumentRepository {

    fun getDocument(
        homeFileName: String,
        homeFileUrl: String
    ): Flow<DocumentState>
}