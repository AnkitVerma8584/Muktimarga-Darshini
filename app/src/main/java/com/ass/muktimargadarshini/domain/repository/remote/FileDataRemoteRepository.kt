package com.ass.muktimargadarshini.domain.repository.remote

import com.ass.muktimargadarshini.ui.presentation.navigation.screens.file_details.modals.FileDataState
import kotlinx.coroutines.flow.Flow

interface FileDataRemoteRepository {

    fun getFileData(
        homeFileName: String,
        homeFileUrl: String
    ): Flow<FileDataState>
}