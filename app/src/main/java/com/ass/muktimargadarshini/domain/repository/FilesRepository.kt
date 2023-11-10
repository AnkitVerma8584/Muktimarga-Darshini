package com.ass.muktimargadarshini.domain.repository

import com.ass.muktimargadarshini.domain.modals.HomeFiles
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.files.modals.FilesDataState
import com.ass.muktimargadarshini.ui.presentation.navigation.screens.files.modals.FilesState
import kotlinx.coroutines.flow.Flow

interface FilesRepository {

    fun getFiles(
        catId: Int,
        subCategoryId: Int,
        subToSubCategoryId: Int
    ): Flow<FilesState>

    fun getFilesData(
        homeFiles: List<HomeFiles>,
    ): Flow<FilesDataState>
}